package org.hummer.task.server.remotecall.unifytask;

import org.hummer.task.common.UnifyTaskStatusType;
import org.hummer.task.server.remotecall.UnifyTask;
import org.hummer.task.server.remotecall.UnifyTaskLog;
import org.hummer.task.server.remotecall.unifytask.biz.UnifyTaskBiz;
import org.hummer.task.server.remotecall.unifytask.biz.UnifyTaskLogBiz;
import org.hummer.task.server.remotecall.unifytask.service.UnifyTaskDispatchService;
import org.hummer.task.vo.TaskAjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.tools.StringUtils;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author zhujinming6
 * @create 2019-10-15 11:26
 * @update 2019-10-15 11:26
 **/
public class UnifyTaskRetryThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(UnifyTaskRetryThread.class);

    private Integer retryTimes;
    private Integer retryTimesLimit;
    private UnifyTaskDispatchService taskDispatchService;
    private UnifyTask task;
    private UnifyTaskBiz taskBiz;
    UnifyTaskLogBiz unifyTaskLogBiz;

    public UnifyTaskRetryThread(Integer retryTimes, Integer retryTimesLimit, UnifyTaskDispatchService taskDispatchService, UnifyTask task, UnifyTaskBiz taskBiz, UnifyTaskLogBiz unifyTaskLogBiz) {
        this.retryTimes = retryTimes;
        this.retryTimesLimit = retryTimesLimit;
        this.taskDispatchService = taskDispatchService;
        this.task = task;
        this.taskBiz = taskBiz;
        this.unifyTaskLogBiz = unifyTaskLogBiz;
    }

    @Override
    public void run() {
        int tryCount = 0;
        // 是否调用成功
        boolean isSuccess = false;
        // 剩余重试次数,期间要是调用成了,则把当前重试次数设置为最新次数,如果失败了,则继续调用,只到上限
        for (int i = 0; i < retryTimesLimit - retryTimes; i++) {
            // 2.3 重试次数加一
            Future<TaskAjaxResult<Boolean>> future = taskDispatchService.dispatchTaskAsync(task.getServer(), task.getTaskNo());
            boolean success = false;
            while (true) {
                if (future.isDone()) {
                    TaskAjaxResult<Boolean> result = null;
                    try {
                        result = future.get();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    } catch (ExecutionException e) {
                        logger.error("", e);
                    }
                    if (result != null && result.getData()) {
                        // 调用成功,跳出线程
                        success = true;
                    }
                    break;
                }
            }
            tryCount++;
            if (success) {
                isSuccess = true;
                break;
            }
        }
        if (tryCount == 0) {
            logger.error("do not retry :retryTimesLimit - retryTimes:{},{}", retryTimesLimit - retryTimes, task.getTaskNo());
        } else {
            retryTimes = retryTimes + tryCount;
            // 所有重试次数已经用完
            UnifyTaskStatusType statusType = null;
            if (!isSuccess) {
                statusType = UnifyTaskStatusType.fail;
            } else if (isSuccess) {
                statusType = UnifyTaskStatusType.executing;
            }
            taskBiz.updateTaskStatusAndTryTimes(task.getServer(), task.getTaskNo(), statusType, retryTimes);
            // 记录重试日志
            logger.error("retry:retryTimesLimit - retryTimes:{},tryCount:{},{}", retryTimesLimit - retryTimes, tryCount, task.getTaskNo());
            UnifyTaskLog log = new UnifyTaskLog();
            log.setCreated(new Date());
            log.setId(StringUtils.uuid32());
            log.setServer(task.getServer());
            log.setName(task.getTaskNo());
            log.setTaskId(task.getId());
            log.setStatus(statusType);
            log.setTaskLog("重试任务:剩余重试次数:" + (retryTimesLimit - retryTimes) + ",当前重试:" + tryCount + "次");
            unifyTaskLogBiz.add(log);
        }
    }
}
