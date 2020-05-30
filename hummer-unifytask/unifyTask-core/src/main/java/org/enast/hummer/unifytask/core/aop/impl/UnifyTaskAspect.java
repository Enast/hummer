package org.enast.hummer.unifytask.core.aop.impl;

import org.enast.hummer.unifytask.core.aop.UnifyTask;
import org.enast.hummer.unifytask.core.common.ServerUtils;
import org.enast.hummer.unifytask.core.remotecall.service.ClientUnifyTaskService;
import org.enast.hummer.unifytask.core.vo.TaskAjaxResult;
import org.enast.hummer.unifytask.core.vo.UnifyTaskStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 统一任务调度切面
 *
 * @author zhujinming6
 * @create 2019-10-11 11:28
 * @update 2019-10-11 11:28
 **/
@Aspect
public class UnifyTaskAspect {

    private static final Logger logger = LoggerFactory.getLogger(UnifyTaskAspect.class);

    @Resource
    ClientUnifyTaskService unifyTaskService;
    @Resource
    ServerUtils serverUtils;

    @Pointcut("@annotation(org.enast.hummer.unifytask.core.aop.UnifyTask)")
    private void pcMethod() {

    }

    @Around("pcMethod()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        UnifyTaskStatus taskStatus = null;
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        UnifyTask unifyTask = method.getAnnotation(UnifyTask.class);
        long time = System.currentTimeMillis();
        try {
            taskStatus = (UnifyTaskStatus) pjp.proceed();
        } catch (Exception e) {
            logger.error("UnifyTaskImpl error", e);
            taskStatus = new UnifyTaskStatus(false, System.currentTimeMillis() - time);
        }
        // 方法不返回状态,这里新建成功的状态
        if (taskStatus == null) {
            taskStatus = new UnifyTaskStatus(true, System.currentTimeMillis() - time);
        }
        if (taskStatus.getDuration() == null) {
            taskStatus.setDuration(System.currentTimeMillis() - time);
        }
        taskStatus.setServer(serverUtils.getContextPath());
        taskStatus.setTaskNo(unifyTask.taskNo());
        logger.info("server:{},taskNo:{},status:{}", taskStatus.getServer(), taskStatus.getTaskNo(), taskStatus.getSuccess());
        TaskAjaxResult<Boolean> result = unifyTaskService.reportTaskStatus(taskStatus);
        logger.info("reportTaskStatus,status:{}", result == null ? null : result.getData());
        return taskStatus;
    }

    @AfterThrowing(pointcut = "pcMethod()", throwing = "e")
    public void doException(JoinPoint jp, Throwable e) {
        if (e != null) {
            Logger logger = LoggerFactory.getLogger(jp.getSignature().getClass());
            logger.error(e.getMessage(), e);
        }
    }
}
