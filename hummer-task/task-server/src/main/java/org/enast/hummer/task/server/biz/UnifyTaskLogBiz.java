package org.enast.hummer.task.server.biz;

import org.enast.hummer.task.server.model.UnifyTaskLog;
import sf.common.wrapper.Page;


/**
 * @author zhujinming6
 * @create 2019-10-15 11:44
 * @update 2019-10-15 11:44
 **/
public interface UnifyTaskLogBiz {

    Integer add(UnifyTaskLog log);
    Page<UnifyTaskLog> pageList(int start, int size);
}
