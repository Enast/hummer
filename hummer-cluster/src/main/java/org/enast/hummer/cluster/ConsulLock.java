package org.enast.hummer.cluster;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.kv.model.PutParams;
import com.ecwid.consul.v1.session.model.NewSession;
import com.ecwid.consul.v1.session.model.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * consul 锁
 *
 * @author zhujinming6
 * @create 2019-10-08 10:45
 * @update 2019-10-08 10:45
 **/
public class ConsulLock {

    private static Logger log = LoggerFactory.getLogger(ConsulLock.class);

    private ConsulClient consulClient;
    /**
     * session超时时间,防止死锁和脑裂出现
     */
    private String sessionTTL;
    private String sessionId = null;
    private String lockKey;
    private String value;

    /**
     * @param consulClient
     * @param sessionTTL   同步锁的sessionTTL
     * @param lockKey      同步锁在consul的KV存储中的Key路径，会自动增加prefix前缀，方便归类查询
     */
    public ConsulLock(ConsulClient consulClient, String sessionTTL, String lockKey, String value) {
        this.consulClient = consulClient;
        this.sessionTTL = sessionTTL;
        this.lockKey = lockKey;
        this.value = value;
    }

    public ConsulLock(ConsulClient consulClient, String lockKey, String value) {
        this.consulClient = consulClient;
        this.lockKey = lockKey;
        this.value = value;
    }

    /**
     * 获取同步锁
     *
     * @param block 是否阻塞，直到获取到锁为止
     * @return
     */
    public Boolean lock(boolean block) {
        if (StringUtils.isBlank(sessionId)) {
            sessionId = createSession();
        }
        while (true) {
            if (acquireKV("lock:[" + value + "]," + LocalDateTime.now())) {
                return true;
            } else if (block) {
                continue;
            } else {
                return false;
            }
        }
    }

    /**
     * 释放同步锁
     *
     * @return
     */
    public Boolean unlock() {
        PutParams params = new PutParams();
        params.setReleaseSession(sessionId);
        return releaseKV("unlock:[" + value + "]," + LocalDateTime.now());
    }

    /**
     * 创建session
     *
     * @return
     */
    public String createSession() {
        NewSession newSession = new NewSession();
        if (sessionTTL != null) {
            newSession.setTtl(sessionTTL);
        }
        sessionId = consulClient.sessionCreate(newSession, null).getValue();
        return sessionId;
    }

    /**
     * 获取指定的session信息
     *
     * @param: sessionId
     * @return: Session对象
     */
    public Session getSession() {
        Session response = null;
        try {
            response = consulClient.getSessionInfo(sessionId, QueryParams.DEFAULT).getValue();
        } catch (Exception e) {
            log.error("", e);
        }
        return response;
    }

    /**
     * 获取指定的session信息
     *
     * @param: sessionId
     * @return: Session对象
     */
    public Session renewSession() {
        Session session = getSession();
        if (session != null) {
            return consulClient.renewSession(sessionId, QueryParams.DEFAULT).getValue();
        } else {
            return session;
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 获取指定key对应的值
     *
     * @param: key
     * @return:
     */
    public GetValue getKVValue() {
        return consulClient.getKVValue(lockKey).getValue();
    }

    /**
     * block获取指定key对应的值
     *
     * @param: key, waitTime, waitIndex
     * @return:
     */
    public GetValue getKVValue(int waitTime, long waitIndex) {
        QueryParams paras = QueryParams.Builder.builder().setWaitTime(waitTime).setIndex(waitIndex).build();
        return consulClient.getKVValue(lockKey, paras).getValue();
    }

    /**
     * 使用Session捕获KV
     *
     * @param value
     * @return
     */
    public Boolean acquireKV(String value) {
        PutParams putParams = new PutParams();
        putParams.setAcquireSession(sessionId);
        return consulClient.setKVValue(lockKey, value, putParams).getValue();
    }

    /**
     * 使用Session释放KV
     *
     * @param value
     * @return
     */
    public Boolean releaseKV(String value) {
        PutParams putParams = new PutParams();
        putParams.setReleaseSession(sessionId);
        boolean result = consulClient.setKVValue(lockKey, value, putParams).getValue();
        consulClient.sessionDestroy(sessionId, null);
        return result;
    }

}
