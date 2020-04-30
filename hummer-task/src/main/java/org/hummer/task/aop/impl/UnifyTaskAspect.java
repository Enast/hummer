package org.hummer.task.aop.impl;

import org.hummer.task.ServerUtils;
import org.hummer.task.client.remotecall.service.ClientUnifyTaskService;
import org.hummer.task.client.remotecall.vo.UnifyTaskStatus;
import org.hummer.task.vo.TaskAjaxResult;
import org.hummer.task.aop.UnifyTask;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.lang.reflect.Method;

/**
 * 统一任务调度切面
 *
 * @author zhujinming6
 * @create 2019-10-11 11:28
 * @update 2019-10-11 11:28
 **/
@Aspect
@Component
public class UnifyTaskAspect {

    private static final Logger logger = LoggerFactory.getLogger(UnifyTaskAspect.class);

    @Resource
    ClientUnifyTaskService unifyTaskService;
    @Resource
    ServerUtils serverUtils;

    @Pointcut("@annotation(org.hummer.task.aop.UnifyTask)")
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
        logger.info("{},{},{}", "server", "taskNo", "status", taskStatus.getServer(), taskStatus.getTaskNo(), taskStatus.getSuccess());
        TaskAjaxResult<Boolean> result = unifyTaskService.reportTaskStatus(taskStatus);
        logger.info("reportTaskStatus", "status", result == null ? null : result.getData());
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
