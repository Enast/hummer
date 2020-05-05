package org.hummer.task.server.model;
import org.hummer.task.core.common.UnifyTaskStatusType;
import sf.core.DBField;
import sf.core.DBObject;
import sf.database.annotations.Comment;

import javax.persistence.*;
import java.util.Date;


/**
 * @author zhujinming6
 * @create 2019-10-08 9:58
 * @update 2019-10-08 9:58
 **/
@Table(name = "unify_task_log")
@Entity
@Comment("统一任务表日志记录表")
public class UnifyTaskLog extends DBObject {

    private static final long serialVersionUID = 8557563325498022141L;

    @Id
    @Column(name = "id")
    @Comment("任务Id")
    private String id;

    @Comment("任务名称")
    @Column(name = "name")
    private String name;

    @Comment("微服务名称(段名称)")
    @Column(name = "server")
    private String server;

    @Comment("任务id")
    @Column(name = "task_id")
    private String taskId;

    @Comment("任务状态")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UnifyTaskStatusType status;

    @Comment("任务log")
    @Column(name = "task_log")
    private String taskLog;

    @Comment("任务执行所用时间")
    @Column(name = "duration")
    private Long duration;

    @Comment("创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Comment("修改时间")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Comment("修改人员")
    @Column(name = "modify_by")
    private String modifyBy;

    @Comment("创建人员")
    @Column(name = "create_by")
    private String createBy;

    public enum Field implements DBField {
        id,
        name,
        server,
        taskId,
        created,
        status,
        modified,
        modifyBy,
        createBy,
        taskLog,
        duration
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(String taskLog) {
        this.taskLog = taskLog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public UnifyTaskStatusType getStatus() {
        return status;
    }

    public void setStatus(UnifyTaskStatusType status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
