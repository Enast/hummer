package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.conmon.PageParam;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.enast.hummer.dynamodel.db.filed.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2020-03-13 15:02
 * @update 2020-03-13 15:02
 **/
@Service
@ConditionalOnBean(DtbCommonDao.class)
public class FiledOperateHandler implements FiledOperate {

    @Autowired
    private DtbCommonDao dtbCommonDao;

    @Override
    public int update(Map<String, Field> fields, List<Map<String, String>> values, String tableName) {
        checkArgs(fields, values);
        return (int) new UpdateFieldOperation(dtbCommonDao, fields, values, tableName).operate();
    }

    @Override
    public int insert(Map<String, Field> fields, List<Map<String, String>> values, String tableName) {
        checkArgs(fields, values);
        return (int) new InsertFieldOperation(dtbCommonDao, fields, values, tableName).operate();
    }

    /**
     * 根据单个字段，批量删除
     *
     * @param field
     * @param deleteIds
     * @param tableName
     * @return
     */
    @Override
    public int delete(Field field, List<String> deleteIds, String tableName) {
        return (int) new DeleteFieldOperation(dtbCommonDao, field, deleteIds, tableName).operate();
    }

    @Override
    public QueryFieldOperation queryBuilder(Map<String, Field> fields, String tableName) {
        return new QueryFieldOperation(dtbCommonDao, fields, tableName);
    }

    /**
     * 分页查询
     *
     * @param fields
     * @param param
     * @param tableName
     * @return
     */
    @Override
    public QueryFieldOperation queryPageBuilder(Map<String, Field> fields, PageParam param, String tableName) {
        return new PageQueryOperation(dtbCommonDao, fields, param, tableName);
    }

    private void checkArgs(Map<String, Field> fields, List<Map<String, String>> values) {
        Assert.notNull(dtbCommonDao, "dtbCommonDao is null");
        Assert.notNull(fields, "tables is null");
        Assert.notNull(values, "values is null");
    }
}
