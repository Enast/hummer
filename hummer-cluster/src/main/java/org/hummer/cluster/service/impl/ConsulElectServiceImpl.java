package org.hummer.cluster.service.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.session.model.Session;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.hummer.cluster.ConsulLock;
import org.hummer.cluster.service.ConsulElectService;
import org.hummer.cluster.utils.MachineCodeUtils;
import org.hummer.cluster.vo.ElectResponse;
import org.hummer.cluster.service.MasterService;
import org.hummer.config.aop.EnableUnifyConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * consul 选举服务
 *
 * @author zhujinming6
 * @create 2019-10-08 19:01
 * @update 2019-10-08 19:01
 **/
@Service
@EnableUnifyConfig
public class ConsulElectServiceImpl implements ConsulElectService {

    private Logger logger = LoggerFactory.getLogger(ConsulElectServiceImpl.class);

    @Value("${cluster.nodeId:}")
    private String nodeId;
    @Value("${cluster.elect.session.ttl:60s}")
    private String sessionTTL;
    @Value("${cluster.elect.blockQuery.waitTime:30}")
    private Integer waitTime;

    private static String CLUSTER_REDIS_KEY = "hummer:cluster:info";
    @Resource
    ConsulClient consulClient;
    @Resource
    MasterService masterService;
    @Resource
    RedisTemplate redisTemplate;

    private static ConsulLock consulLock;

    @Override
    public void work(String electName) {
        if (consulLock == null) {
            if (StringUtils.isBlank(nodeId)) {
                nodeId = MachineCodeUtils.getCode();
            }
            consulLock = new ConsulLock(consulClient, sessionTTL, electName, nodeId);
        }
        logger.info("start first leader election");
        // 先选举一次
        ElectResponse electResponse = elect();
        // 主节点相关业务启动和非主节点相关业务停止
        masterService.businessSwitch(electResponse, nodeId);
        logger.info("", "result", "manager", electResponse.getElectResult(), electResponse.getLeaderId());
        //创建一个可重用的固定线程池数的线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ConsulElect-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 180, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        executorService.execute(() -> {
            watch(electResponse);
        });
        logger.info("finished first leader election");
    }

    /**
     * 监控选举
     *
     * @param:
     * @return:
     */
    @Override
    public void watch(ElectResponse electResponse) {
        logger.info("watch leader election");
        long waitIndex = electResponse.getModifyIndex() + 1;
        do {
            try {
                logger.info("start leader watch query", "waitTime", "waitIndex", waitTime, waitIndex);
                // 阻塞查询,监听kv状态,及时响应变更
                GetValue kv = consulLock.getKVValue(waitTime, waitIndex);
                // kv被删除或者kv绑定的session不存在
                if (null == kv || StringUtils.isBlank(kv.getSession())) {
                    logger.info("leader missing, start election right away");
                    electResponse = elect();
                    masterService.businessSwitch(electResponse, nodeId);
                    waitIndex = electResponse.getModifyIndex() + 1;
                    logger.info("elect result, current manager", "result", "manager", electResponse.getElectResult(), electResponse.getLeaderId());
                } else {
                    long kvModifyIndex = kv.getModifyIndex();
                    waitIndex = kvModifyIndex++;
                    if (consulLock.getSessionId().equals(kv.getSession())) {
                        // 当前节点是主节点,更新session
                        consulLock.renewSession();
                        electResponse.setElectResult(true);
                    } else {
                        electResponse.setElectResult(false);
                    }
                    masterService.businessSwitch(electResponse, nodeId);
                }
            } catch (Exception ex) {
                logger.error("leader watch error", ex);
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
        }
        while (true);
    }

    /**
     * 执行选举
     *
     * @param:
     * @return:
     */
    @Override
    public ElectResponse elect() {
        ElectResponse response = new ElectResponse();
        Boolean electResult = false;
        // 创建一个关联到当前节点的Session
        if (StringUtils.isNotEmpty(consulLock.getSessionId())) {
            logger.info("consulLock.getSessionId() is not null");
            Session s = consulLock.getSession();
            if (null == s) {
                logger.info("session not exits {}", "seesionId", consulLock.getSessionId());
                consulLock.createSession();
            }
        } else {
            ElectResponse cluster = (ElectResponse) redisTemplate.opsForValue().get(CLUSTER_REDIS_KEY);
            // 注意：如果程序关闭后很快启动，session关联的健康检查可能不会失败，所以session不会失效
            // 这时候可以把程序创建的sessionId保存起来，重启后首先尝试用上次的sessionId，
            if (cluster != null && nodeId.equals(getNodeId(cluster.getLeaderId())) && StringUtils.isNotBlank(cluster.getSessionId())) {
                logger.info("ElectResponse cluster is not  null nodeId.equals(cluster.getLeaderId())");
                // 当前节点是主节点
                consulLock.setSessionId(cluster.getSessionId());
                Session s = consulLock.getSession();
                if (null == s) {
                    logger.info("session not exits", "sessionId", cluster.getSessionId());
                    // session 已经不存在
                    consulLock.createSession();
                }
            } else {
                logger.info("ElectResponse cluster is null or nodeId.notEquals(cluster.getLeaderId())", "leader", cluster == null ? "" : cluster.getLeaderId());
                consulLock.createSession();
            }
            logger.info("session id", "sessionId", consulLock.getSessionId());
        }
        // 抢占 KV 成为leader
        electResult = consulLock.lock(false);
        // 无论参选成功与否，获取当前的Leader
        GetValue kv = consulLock.getKVValue();
        response.setElectResult(electResult);
        response.setLeaderId(kv.getDecodedValue());
        response.setModifyIndex(kv.getModifyIndex());
        if (electResult) {
            response.setSessionId(consulLock.getSessionId());
            redisTemplate.opsForValue().set(CLUSTER_REDIS_KEY, response);
        }
        return response;
    }

    private String getNodeId(String leaderId) {
        if (StringUtils.isBlank(leaderId)) {
            return StringUtils.EMPTY;
        }
        int startIndex = leaderId.indexOf("[") + 1;
        int endIndex = leaderId.lastIndexOf("]");
        if (startIndex > endIndex || startIndex > leaderId.length()) {
            return StringUtils.EMPTY;
        }
        return leaderId.substring(startIndex, endIndex);
    }

}
