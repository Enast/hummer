package org.enast.hummer.unifytask.core.vo;

/**
 * 任务更新实体
 *
 * @author zhujinming6
 * @create 2019-10-12 15:42
 * @update 2019-10-12 15:42
 **/
public class UnifyTaskUpdate extends BasicTask {


    public UnifyTaskUpdate() {
    }

    public UnifyTaskUpdate(String taskName, String taskNo, String server, String cron) {
        super(taskName, taskNo, server, cron);
    }

    public UnifyTaskUpdate(String taskName, String taskNo, String cron) {
        super(taskName, taskNo, null, cron);
    }

}
