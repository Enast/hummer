package org.enast.hummer.unifytask.client.service;

import org.enast.hummer.unifytask.core.vo.UnifyTaskRegister;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2019-10-10 18:53
 * @update 2019-10-10 18:53
 **/
public interface TaskExecuteService {

    /**
     * 启动任务
     *
     * @param taskName
     * @return
     */
    Boolean startTask(String taskName);

    void tasksRegister(List<UnifyTaskRegister> registers);
}
