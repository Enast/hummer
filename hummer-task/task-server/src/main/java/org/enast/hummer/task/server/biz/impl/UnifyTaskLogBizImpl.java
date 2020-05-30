package org.enast.hummer.task.server.biz.impl;

import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.task.core.common.UnifyTaskStatusType;
import org.enast.hummer.task.server.model.UnifyTaskLog;
import org.enast.hummer.task.server.biz.UnifyTaskLogBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sf.common.wrapper.Page;
import sf.database.dao.DBClient;
import sf.database.mapper.DaoMapperImpl;
import sf.dsl.Example;

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
    public Page<UnifyTaskLog> pageList(String search, int start, int size, UnifyTaskStatusType status) {
        UnifyTaskLog log = new UnifyTaskLog();
        log.useQuery().orderByDesc(UnifyTaskLog.Field.created);
        boolean hasAnd = false;
        Example.Criteria c = log.useQuery().createCriteria();
        if (StringUtils.isNotBlank(search)) {
            c.like(UnifyTaskLog.Field.name, "%" + search + "%").or()
                    .like(UnifyTaskLog.Field.server, "%" + search + "%");
            hasAnd = true;
        }
        if (status!=null && status != UnifyTaskStatusType.all) {
            if(hasAnd){
                c.and();
            }
            c.eq(UnifyTaskLog.Field.status,status);
        }
        return selectPage(log, start, size);
    }
}
