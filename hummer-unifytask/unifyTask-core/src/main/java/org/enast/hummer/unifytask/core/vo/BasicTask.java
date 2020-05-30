package org.enast.hummer.unifytask.core.vo;

/**
 * 基础任务实体
 *
 * @author zhujinming6
 * @create 2019-11-23 13:56
 * @update 2019-11-23 13:56
 **/
public class BasicTask {

    /**
     * 任务名称
     */
    protected String taskName;

    /**
     * 任务编号
     */
    protected String taskNo;

    /**
     * 服务编号
     */
    protected String server;

    /**
     * 执行cron表达式
     */
    protected String cron;

    public BasicTask() {
    }

    public BasicTask(String taskName, String taskNo, String server, String cron) {
        this.taskName = taskName;
        this.taskNo = taskNo;
        this.server = server;
        this.cron = cron;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
