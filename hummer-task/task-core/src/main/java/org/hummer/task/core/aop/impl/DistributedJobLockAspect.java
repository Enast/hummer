package org.hummer.task.core.aop.impl;

import org.hummer.task.core.aop.DistributedJoLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 分布式任务调度切面
 *
 * @author zhujinming6
 * @create 2019-08-26 16:10
 * @update 2019-08-26 16:10
 **/
@Aspect
@ConditionalOnProperty(name = "hummer.redisson.use", havingValue = "true")
public class DistributedJobLockAspect {

    private final Logger log = LoggerFactory.getLogger(DistributedJobLockAspect.class);

    @Resource
    RedissonClient redissonClient;

    @Pointcut("@annotation(org.hummer.task.core.aop.DistributedJoLock)")
    public void point() {
    }

    @Around(value = "point()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();
        //方法参数数组
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        //执行定时任务的方法
        Method method = targetClass.getMethod(methodName, parameterTypes);
        // 自定义任务注解
        DistributedJoLock jobAnnotation = method.getAnnotation(DistributedJoLock.class);
        // spring 任务注解
        Scheduled scheduledAnnotation = method.getAnnotation(Scheduled.class);
        // 分布式锁持有时间
        Long leaseTime = 0L;
        String cron = scheduledAnnotation.cron();
        String jobKey = jobAnnotation.lockKey();
        // 获取Scheduled注解的定时任务时间周期
        try {
            if (StringUtils.isBlank(cron)) {
                log.info("DistributedJobLockAspect cron is null", "jobKey", jobKey);
                long fixedRate = scheduledAnnotation.fixedRate() == -1 ? (StringUtils.isBlank(scheduledAnnotation.fixedRateString()) ? -1 : Long.parseLong(scheduledAnnotation.fixedRateString())) : scheduledAnnotation.fixedRate();
                long fixedDelay = scheduledAnnotation.fixedDelay() == -1 ? (StringUtils.isBlank(scheduledAnnotation.fixedDelayString()) ? -1 : Long.parseLong(scheduledAnnotation.fixedDelayString())) : scheduledAnnotation.fixedDelay();
                // 时间间隔为,周期频率时间和延时时间总和
                leaseTime = leaseTime + (fixedRate == -1 ? 0 : fixedRate) + (fixedDelay == -1 ? 0 : fixedDelay);
            } else {
                CronTriggerImpl cronTrigger = new CronTriggerImpl();
                try {
                    cronTrigger.setCronExpression(cron);
                    Date date = cronTrigger.getFireTimeAfter(new Date(currentTimeMillis));
                    if (date == null) {
                        // 默认1s中
                        leaseTime = 1000L;
                    } else {
                        leaseTime = date.getTime() - currentTimeMillis;
                    }
                    log.info("DistributedJobLockAspect nextFireTime", "jobKey", "date", jobKey, date);
                } catch (ParseException e) {
                    log.error("", e);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // 未设置任务周期间隔,不执行该定时任务
        if (leaseTime == 0) {
            log.info("DistributedJobLockAspect leaseTime is null", "jobKey", jobKey);
            // 执行任务,不影响任务执行
            pjp.proceed();
        } else {
            log.info("leaseTime is", "jobKey", "leaseTime", jobKey, leaseTime);
            RLock lock = redissonClient.getLock("DISTRIBUTED_JOB_" + jobAnnotation.lockKeyPre() + "_" + jobKey);
            lock.lock(leaseTime, TimeUnit.MILLISECONDS);
            try {
                // 执行任务
                pjp.proceed();
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
        return null;
    }

    @AfterThrowing(value = "point()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        throw new RuntimeException(ex);
    }

}
