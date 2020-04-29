package org.hummer.task.client.remotecall.vo;

import java.util.Date;

/**
 * 任务状态实体
 *
 * @author zhujinming6
 * @create 2019-10-11 10:41
 * @update 2019-10-11 10:41
 **/
public class UnifyTaskStatus {

    /**
     * 任务执行结果
     */
    private Boolean success;
    private String server;
    private String taskNo;
    private Date time;
    private Long duration;

    public UnifyTaskStatus(Boolean success, Long duration) {
        this.success = success;
        this.duration = duration;
        time = new Date();
    }

    public UnifyTaskStatus() {
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
