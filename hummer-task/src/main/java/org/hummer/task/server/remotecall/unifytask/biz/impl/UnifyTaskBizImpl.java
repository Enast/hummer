package org.hummer.task.server.remotecall.unifytask.biz.impl;

import org.hummer.task.common.UnifyTaskStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sf.database.dao.DBClient;
import sf.database.mapper.DaoMapperImpl;
import org.hummer.task.server.remotecall.UnifyTask;
import org.hummer.task.server.remotecall.unifytask.biz.UnifyTaskBiz;
import java.util.Date;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2019-10-11 20:10
 * @update 2019-10-11 20:10
 **/
@Repository
public class UnifyTaskBizImpl extends DaoMapperImpl<UnifyTask> implements UnifyTaskBiz {

    public UnifyTaskBizImpl(@Autowired DBClient dbClient) {
        super(UnifyTask.class, dbClient);
    }

    @Override
    public List<UnifyTask> findList() {
        UnifyTask unifyTask = new UnifyTask();
        unifyTask.useQuery().createCriteria().eq(UnifyTask.Field.dataValid, true);
        unifyTask.useQuery().orderByAsc(UnifyTask.Field.nextExecuteTime);
        return selectList(unifyTask);
    }

    @Override
    public UnifyTask findOne(String taskNo, String server) {
        UnifyTask unifyTask = new UnifyTask();
        unifyTask.useQuery().createCriteria().eq(UnifyTask.Field.taskNo, taskNo)
                .and().eq(UnifyTask.Field.server, server);
        return single(unifyTask);
    }

    @Override
    public Integer add(UnifyTask unifyTask) {
        return insert(unifyTask);
    }

    @Override
    public void addList(List<UnifyTask> taskList) {
        insertBatch(taskList);
    }

    @Override
    public void updateList(List<UnifyTask> taskListUpdate) {
        updateBatch(taskListUpdate);
    }

    @Override
    public void updateTaskStatus(String server, String taskNo, UnifyTaskStatusType statusType) {
        UnifyTask unifyTask = new UnifyTask();
        unifyTask.setStatus(statusType);
        unifyTask.setModified(new Date());
        unifyTask.useQuery().createCriteria().eq(UnifyTask.Field.server, server)
                .and().eq(UnifyTask.Field.taskNo, taskNo);
        update(unifyTask);
    }

    @Override
    public void updateTaskStatusAndTryTimes(String server, String taskNo, UnifyTaskStatusType statusType, int retryTimes) {
        UnifyTask unifyTask = new UnifyTask();
        unifyTask.setStatus(statusType);
        unifyTask.setModified(new Date());
        unifyTask.setRetryTimes(retryTimes);
        unifyTask.useQuery().createCriteria().eq(UnifyTask.Field.server, server)
                .and().eq(UnifyTask.Field.taskNo, taskNo);
        update(unifyTask);
    }

    @Override
    public void updateTaskStatusAndLastExcuteTime(String server, String taskNo, UnifyTaskStatusType finished, Date time) {
        UnifyTask unifyTask = new UnifyTask();
        unifyTask.setLastExecuteTime(time);
        unifyTask.setModified(new Date());
        unifyTask.setStatus(finished);
        unifyTask.useQuery().createCriteria().eq(UnifyTask.Field.server, server)
                .and().eq(UnifyTask.Field.taskNo, taskNo);
        update(unifyTask);
    }
}
