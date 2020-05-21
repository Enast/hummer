package org.enast.hummer.task.server.biz.impl;

import org.enast.hummer.task.server.model.UnifyTaskLog;
import org.enast.hummer.task.server.biz.UnifyTaskLogBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sf.common.wrapper.Page;
import sf.database.dao.DBClient;
import sf.database.mapper.DaoMapperImpl;

/**
 * @author zhujinming6
 * @create 2019-10-15 11:44
 * @update 2019-10-15 11:44
 **/
@Repository
public class UnifyTaskLogBizImpl extends DaoMapperImpl<UnifyTaskLog> implements UnifyTaskLogBiz {

    public UnifyTaskLogBizImpl(@Autowired DBClient dbClient) {
        super(UnifyTaskLog.class, dbClient);
    }

    @Override
    public Integer add(UnifyTaskLog log) {
        return insert(log);
    }

    @Override
    public Page<UnifyTaskLog> pageList(int start, int size) {
        UnifyTaskLog log = new UnifyTaskLog();
        log.useQuery().orderByDesc(UnifyTaskLog.Field.created);
        return selectPage(log, start, size);
    }
}
