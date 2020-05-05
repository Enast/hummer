package org.enast.hummer.task.server.service;

import org.enast.hummer.task.core.vo.TaskAjaxResult;

import java.util.concurrent.Future;

/**
 * @author zhujinming6
 * @create 2019-10-12 13:07
 * @update 2019-10-12 13:07
 **/
public interface UnifyTaskDispatchService {
    /**
     * 异步调用任务
     * 批量调度任务时,可以使用,改方法,提高调度的及时性
     *
     * @param server
     * @param taskName
     * @return
     */
    Future<TaskAjaxResult<Boolean>> dispatchTaskAsync(String server, String taskName);

    /**
     * 同步
     *
     * @param server
     * @param taskName
     * @return
     */
    TaskAjaxResult<Boolean> dispatchTask(String server, String taskName);
}
