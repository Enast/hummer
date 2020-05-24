package org.enast.hummer.task.server.web.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.enast.hummer.task.core.common.UnifyTaskStatusType;
import org.enast.hummer.task.server.model.UnifyTask;

import java.util.Date;

/**
 * @author zhujinming6
 * @create 2020-05-21 15:03
 * @update 2020-05-21 15:03
 **/
@Data
@NoArgsConstructor
public class TaskVO {

    private String id;

    private String name;

    private String taskNo;

    private String server;

    private String preTaskId;

    private String taskCron;

    private Integer retryTimes;

    private Integer retryTimesLimit;

    private Long lastExecuteTime;

    private Long nextExecuteTime;

    private Boolean forceExecute;

    private Boolean resetNextExecuteTime;

    private UnifyTaskStatusType status;

    private Long interval;

    private Long created;

    private Long modified;

    private Boolean dataValid;

    public TaskVO(UnifyTask task) {
        this.id = task.getId();
        this.name = task.getName();
        this.taskNo = task.getTaskNo();
        this.taskCron = task.getTaskCron();
        this.server = task.getServer();
        this.preTaskId = task.getPreTaskId();
        this.retryTimes = task.getRetryTimes();
        this.retryTimesLimit = task.getRetryTimesLimit();
        this.lastExecuteTime = task.getLastExecuteTime() == null ? null : task.getLastExecuteTime().getTime();
        this.nextExecuteTime = task.getNextExecuteTime() == null ? null : task.getNextExecuteTime().getTime();
        this.forceExecute = task.getForceExecute();
        this.resetNextExecuteTime = task.getResetNextExecuteTime();
        this.status = task.getStatus();
        this.interval = task.getInterval();
        this.created = task.getCreated() == null ? null : task.getCreated().getTime();
        this.modified = task.getModified() == null ? null : task.getModified().getTime();
        this.dataValid = task.getDataValid();
    }
}
