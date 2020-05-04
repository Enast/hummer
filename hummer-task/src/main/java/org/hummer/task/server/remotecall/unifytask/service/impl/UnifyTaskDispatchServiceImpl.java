package org.hummer.task.server.remotecall.unifytask.service.impl;

import org.hummer.task.server.remotecall.service.ServerUnifyTaskService;
import org.hummer.task.server.remotecall.unifytask.service.UnifyTaskDispatchService;
import org.hummer.task.vo.TaskAjaxResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author zhujinming6
 * @create 2019-10-12 13:07
 * @update 2019-10-12 13:07
 **/
@Service
public class UnifyTaskDispatchServiceImpl implements UnifyTaskDispatchService {

    @Resource
    ServerUnifyTaskService unifyTaskService;

    /**
     * 异步调用任务
     *
     * @param server
     * @param taskName
     * @return
     */
    @Override
    @Async
    public Future<TaskAjaxResult<Boolean>> dispatchTaskAsync(String server, String taskName) {
        Future<TaskAjaxResult<Boolean>> resultFuture = new AsyncResult(unifyTaskService.dispatchTask(server, taskName));
        return resultFuture;
    }

    /**
     * 同步
     *
     * @param server
     * @param taskName
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> dispatchTask(String server, String taskName) {
        return unifyTaskService.dispatchTask(server, taskName);
    }
}
