package org.hummer.cluster.service.impl;

import org.hummer.cluster.event.ElectEvent;
import org.hummer.cluster.ClusterStatus;
import org.hummer.cluster.service.MasterService;
import org.hummer.cluster.vo.ElectResponse;
import log.HikLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 注解特定业务逻辑处理
 *
 * @author zhujinming6
 * @create 2019-10-08 21:29
 * @update 2019-10-08 21:29
 **/
@Service
public class MasterServiceImpl implements MasterService, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 主节点相关业务启动(选举成功)和非主节点相关业务停止(选举失败)
     *
     * @param electResponse 选举结果
     */
    @Override
    public void businessSwitch(ElectResponse electResponse, String nodeId) {
        if (electResponse == null) {
            logger.info("electResponse is null"));
            return;
        }
        ClusterStatus.setMaster(electResponse.getElectResult());
        logger.info("deal elect", "result", "master")), nodeId, electResponse.getElectResult());
        context.publishEvent(new ElectEvent(nodeId, electResponse));
    }

    /**
     * 判断当前节点是否是主节点
     *
     * @return
     */
    @Override
    public Boolean master() {
        return ClusterStatus.isMaster();
    }
}
