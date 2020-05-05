package org.enast.hummer.task.server.model;

import org.enast.hummer.task.core.common.UnifyTaskStatusType;
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
@Table(name = "unify_task")
@Entity
@Comment("统一任务表")
public class UnifyTask extends DBObject {

    private static final long serialVersionUID = 8557563495498022141L;

    @Id
    @Column(name = "id")
    @Comment("任务Id")
    private String id;

    @Comment("任务名称")
    @Column(name = "name")
    private String name;

    @Comment("任务名称")
    @Column(name = "task_no")
    private String taskNo;

    @Comment("微服务名称(段名称)")
    @Column(name = "server")
    private String server;

    @Comment("前置任务id,多个任务逗号隔开")
    @Column(name = "pre_task_id")
    private String preTaskId;

    @Comment("任务执行corn表达式")
    @Column(name = "task_cron")
    private String taskCron;

    @Comment("任务重试次数")
    @Column(name = "retry_times")
    private Integer retryTimes;

    @Comment("任务重试次数上限")
    @Column(name = "retry_times_limit")
    private Integer retryTimesLimit;

    @Comment("任务最后执行时间")
    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    @Comment("任务下次执行时间")
    @Column(name = "next_execute_time")
    private Date nextExecuteTime;

    @Comment("任务是否需要强制执行一次,执行完之后会被修改为false")
    @Column(name = "force_execute")
    private Boolean forceExecute;

    @Comment("是否刷新任务下次执行时间,下次调度任务周期时会刷新时间")
    @Column(name = "reset_next_execute_time", columnDefinition = " boolean DEFAULT false ")
    private Boolean resetNextExecuteTime = Boolean.FALSE;

    @Comment("任务状态")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UnifyTaskStatusType status;

    @Comment("任务执行间隔")
    @Column(name = "interval")
    private Long interval;

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

    @Comment("true：有效 false：无效（逻辑删除）;默认为有效")
    @Column(name = "data_valid", columnDefinition = " boolean DEFAULT true ")
    private Boolean dataValid = Boolean.TRUE;

    public enum Field implements DBField {
        id, name, server, preTaskId, taskCron, retryTimes, lastExecuteTime, nextExecuteTime, forceExecute, resetNextExecuteTime, status, created,
        modified, dataValid, modifyBy, createBy, taskNo, interval, retryTimesLimit
    }

    public Integer getRetryTimesLimit() {
        return retryTimesLimit;
    }

    public void setRetryTimesLimit(Integer retryTimesLimit) {
        this.retryTimesLimit = retryTimesLimit;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
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

    public String getPreTaskId() {
        return preTaskId;
    }

    public void setPreTaskId(String preTaskId) {
        this.preTaskId = preTaskId;
    }

    public String getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Boolean getForceExecute() {
        return forceExecute;
    }

    public void setForceExecute(Boolean forceExecute) {
        this.forceExecute = forceExecute;
    }

    public Boolean getResetNextExecuteTime() {
        return resetNextExecuteTime;
    }

    public void setResetNextExecuteTime(Boolean resetNextExecuteTime) {
        this.resetNextExecuteTime = resetNextExecuteTime;
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

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}
