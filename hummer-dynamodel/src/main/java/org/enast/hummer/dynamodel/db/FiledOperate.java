package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.conmon.PageParam;
import org.enast.hummer.dynamodel.db.filed.QueryFieldOperation;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2020-03-12 18:50
 * @update 2020-03-12 18:50
 **/
public interface FiledOperate {

    int update(Map<String, Field> fields, List<Map<String, String>> values, String tableName);

    int insert(Map<String, Field> fields, List<Map<String, String>> values, String tableName);

    /**
     * 根据单个字段，批量删除
     *
     * @param field
     * @param deleteIds
     * @param tableName
     * @return
     */
    int delete(Field field, List<String> deleteIds, String tableName);

    QueryFieldOperation queryBuilder(Map<String, Field> fields, String tableName);

    /**
     * 分页查询
     *
     * @param fields
     * @param param
     * @param tableName
     * @return
     */
    QueryFieldOperation queryPageBuilder(Map<String, Field> fields, PageParam param, String tableName);
}
