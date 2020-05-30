package org.enast.hummer.unifytask.core.remotecall.service;

import org.enast.hummer.unifytask.core.vo.*;

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
