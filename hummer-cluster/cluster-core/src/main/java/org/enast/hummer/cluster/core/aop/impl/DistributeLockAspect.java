package org.enast.hummer.cluster.core.aop.impl;

import com.ecwid.consul.v1.ConsulClient;
import org.enast.hummer.cluster.core.ConsulLock;
import org.enast.hummer.cluster.core.aop.DistributeLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * 分布式锁切面处理逻辑类
 *
 * @author zhujinming6
 * @create 2019-11-13 10:25
 * @update 2019-11-13 10:25
 **/
@Aspect
public class DistributeLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(DistributeLockAspect.class);

    @Autowired
    ConsulClient consulClient;


    @Pointcut("@annotation(org.enast.hummer.cluster.core.aop.DistributeLock)")
    private void pcMethod() {

    }

    @Around("pcMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);
        ConsulLock lock = null;
        int ttl = distributeLock.ttl();
        try {
            if (ttl == -1 || ttl < 0) {
                lock = new ConsulLock(consulClient, distributeLock.key(), System.currentTimeMillis() + "");
            } else {
                lock = new ConsulLock(consulClient, ttl + "s", distributeLock.key(), System.currentTimeMillis() + "");
            }
            Boolean result = lock.lock(distributeLock.block());
            if (result) {
                logger.info("get the lock");
                return pjp.proceed();
            }
            logger.info("finished exe");
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
        return null;
    }
}
