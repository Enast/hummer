package org.enast.hummer.task.server.web.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.enast.hummer.task.core.common.UnifyTaskStatusType;
import org.enast.hummer.task.server.model.UnifyTaskLog;

import java.util.Date;

/**
 * @author zhujinming6
 * @create 2020-05-21 18:59
 * @update 2020-05-21 18:59
 **/
@Data
@NoArgsConstructor
public class TaskLogVO {

    private String id;

    private String name;

    private String server;

    private String taskId;

    private UnifyTaskStatusType status;

    private String taskLog;

    private Long duration;

    private Date created;

    public TaskLogVO(UnifyTaskLog log) {
        this.id = log.getId();
        this.name = log.getName();
        this.server = log.getServer();
        this.taskId = log.getTaskId();
        this.status = log.getStatus();
        this.taskLog = log.getTaskLog();
        this.duration = log.getDuration();
        this.created = log.getCreated();
    }
}
