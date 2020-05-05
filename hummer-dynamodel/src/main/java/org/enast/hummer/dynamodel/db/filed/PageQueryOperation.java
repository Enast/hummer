package org.enast.hummer.dynamodel.db.filed;

import org.enast.hummer.dynamodel.conmon.Page;
import org.enast.hummer.dynamodel.conmon.PageParam;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2020-03-28 14:00
 * @update 2020-03-28 14:00
 **/
public class PageQueryOperation extends QueryFieldOperation {

    PageParam param;
    private StringBuilder limitSql;

    public PageQueryOperation(DtbCommonDao dtbCommonDao, Map<String, Field> fields, PageParam param, String tableName) {
        super(dtbCommonDao, fields, tableName);
        this.param = param;
    }

    public PageQueryOperation limit() {
        limitSql = new StringBuilder();
        if (StringUtils.isBlank(stringBuilder) || param == null) {
            throw new RuntimeException();
        }
        limitSql.append(" limit ").append(param.getPageSize()).append(" offset ").append((param.getPageNo() - 1) * param.getPageSize());
        return this;
    }

    @Override
    public List<Map<String, Object>> query() {
        if (StringUtils.isBlank(stringBuilder)) {
            return null;
        }
        return dtbCommonDao.query(stringBuilder.toString() + where.toString() + limitSql.toString());
    }

    public PageQueryOperation limitParam(PageParam param) {
        this.param = param;
        return this;
    }

    @Override
    public Page<Map<String, Object>> findByPage() {
        Page<Map<String, Object>> result = new Page<>();
        Long total = count();
        result.setTotal(total);
        result.setPageSize(param.getPageSize());
        limit();
        List<Map<String, Object>> data = query();
        result.setRows(data);
        return result;
    }
}
