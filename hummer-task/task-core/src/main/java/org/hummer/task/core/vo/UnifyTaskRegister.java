package org.hummer.task.core.vo;

/**
 * 任务注册实体
 *
 * @author zhujinming6
 * @create 2019-10-12 15:42
 * @update 2019-10-12 15:42
 **/
public class UnifyTaskRegister extends BasicTask {


    public UnifyTaskRegister() {
    }

    public UnifyTaskRegister(String taskName, String taskNo, String server, String cron) {
        super(taskName, taskNo, server, cron);
    }

}
