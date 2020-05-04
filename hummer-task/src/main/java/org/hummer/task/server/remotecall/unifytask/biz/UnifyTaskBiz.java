package org.hummer.task.server.remotecall.unifytask.biz;

import java.util.Date;
import org.hummer.task.server.remotecall.UnifyTask;
import java.util.List;
import org.hummer.task.common.UnifyTaskStatusType;


/**
 * @author zhujinming6
 * @create 2019-10-11 20:10
 * @update 2019-10-11 20:10
 **/
public interface UnifyTaskBiz {

    List<UnifyTask> findList();

    UnifyTask findOne(String taskNo, String server);

    Integer add(UnifyTask unifyTask);

    void addList(List<UnifyTask> taskList);

    void updateList(List<UnifyTask> taskListUpdate);

    void updateTaskStatus(String server, String taskNo, UnifyTaskStatusType executing);

    void updateTaskStatusAndTryTimes(String server, String taskNo, UnifyTaskStatusType executing, int retryTimes);

    void updateTaskStatusAndLastExcuteTime(String server, String taskNo, UnifyTaskStatusType finished, Date time);
}
