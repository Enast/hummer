package org.hummer.task.client.remotecall.service;

import org.hummer.task.client.remotecall.vo.BasicTask;
import org.hummer.task.client.remotecall.vo.UnifyTaskStatus;
import org.hummer.task.vo.TaskAjaxResult;
import org.hummer.task.client.remotecall.vo.UnifyTaskRegister;
import org.hummer.task.client.remotecall.vo.UnifyTaskUpdate;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2019-10-11 10:38
 * @update 2019-10-11 10:38
 **/
public interface ClientUnifyTaskService {

    /**
     * 任务执行完状态上报
     *
     * @param taskStatus
     * @return
     */
    TaskAjaxResult<Boolean> reportTaskStatus(UnifyTaskStatus taskStatus);

    /**
     * 任务发现
     *
     * @param registers
     * @return
     */
    TaskAjaxResult<Boolean> tasksRegister(List<UnifyTaskRegister> registers);

    /**
     * 任务更新执行时间
     *
     * @param updateList
     * @return
     */
    TaskAjaxResult<Boolean> tasksUpdate(List<UnifyTaskUpdate> updateList);

    /**
     * 查询任务
     *
     * @param taskNo
     * @return
     */
    TaskAjaxResult<BasicTask> taskByNo(String taskNo);
}
