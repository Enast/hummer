package org.enast.hummer.cluster.aop.impl;

import org.enast.hummer.cluster.service.MasterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 判断是否主节点注解,处理逻辑类
 *
 * @author zhujinming6
 * @create 2019-10-12 13:42
 * @update 2019-10-12 13:42
 **/
@Aspect
public class ClusterMasterAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClusterMasterAspect.class);

    @Resource
    MasterService masterService;

    @Pointcut("@annotation(org.enast.hummer.cluster.aop.ClusterMaster)")
    private void pcMethod() {

    }

    @Around("pcMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (masterService.master()) {
            logger.info("it is master");
            return pjp.proceed();
        }
        logger.info("it is not master");
        return null;
    }

}
