package org.enast.hummer.dynamodel.db.filed;


import org.enast.hummer.dynamodel.conmon.CollectionUtils;
import org.enast.hummer.dynamodel.conmon.Page;
import org.enast.hummer.dynamodel.db.DBOperation;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2020-03-17 18:52
 * @update 2020-03-17 18:52
 **/
public class QueryFieldOperation extends AbstractFieldOperation {

    protected StringBuilder where = new StringBuilder();
    protected StringBuilder countSql = new StringBuilder();

    public QueryFieldOperation(DtbCommonDao dtbCommonDao, Map<String, Field> fields, String tableName) {
        super(dtbCommonDao, fields, null, tableName, DBOperation.SELECT);
    }

    /**
     *
     */
    @Override
    public void before() {
        if (StringUtils.isBlank(stringBuilder)) {
            stringBuilder.append(" select ");
            if (CollectionUtils.isEmpty(fields)) {
                stringBuilder.append(" * ");
            } else {
                final int[] length = {fields.size()};
                final int[] i = {0};
                fields.entrySet().stream().forEach(f -> {
                    stringBuilder.append("\"").append(f.getValue().getCode()).append("\"");
                    i[0]++;
                    if (i[0] < length[0]) {
                        stringBuilder.append(",");
                    }
                });
            }
            stringBuilder.append(" from \"").append(tableName).append("\" ");
        }
        if (StringUtils.isBlank(countSql)) {
            countSql.append(" select count(id) ");
            countSql.append(" from \"").append(tableName).append("\" ");
        }
    }

    @Override
    public List<Map<String, Object>> query() {
        if (StringUtils.isBlank(stringBuilder)) {
            return null;
        }
        return dtbCommonDao.query(stringBuilder.toString() + where.toString());
    }

    @Override
    public Long count() {
        if (StringUtils.isBlank(countSql)) {
            return 0L;
        }
        List<Map<String, Object>> res = dtbCommonDao.query(countSql.toString() + where.toString());
        if (CollectionUtils.isEmpty(res)) {
            return 0L;
        }
        Map<String, Object> map = res.get(0);
        if (map == null) {
            return 0L;
        }
        return (Long) map.getOrDefault("count", 0);
    }

    @Override
    public QueryFieldOperation operate() {
        before();
        return this;
    }

    public QueryFieldOperation where(String code) {
        if (StringUtils.isBlank(where)) {
            where.append(" where  ");
        }
        where.append(" \"").append(code).append("\"");
        return this;
    }

    public QueryFieldOperation and() {
        where.append(" and ");
        return this;
    }

    public QueryFieldOperation or() {
        where.append(" or ");
        return this;
    }

    public QueryFieldOperation eq(Object value) {
        if (value instanceof String) {
            where.append("='").append(value).append("' ");
        } else {
            where.append("=").append(value).append(" ");
        }
        return this;
    }

    public QueryFieldOperation neq(Object value) {
        if (value instanceof String) {
            where.append("!='").append(value).append("' ");
        } else {
            where.append("!=").append(value).append(" ");
        }
        return this;
    }


    public QueryFieldOperation lt(Object value) {
        if (value instanceof String) {
            where.append("<'").append(value).append("' ");
        } else {
            where.append("<").append(value).append(" ");
        }
        return this;
    }

    public QueryFieldOperation gt(Object value) {
        if (value instanceof String) {
            where.append(">'").append(value).append("' ");
        } else {
            where.append(">").append(value).append(" ");
        }
        return this;
    }

    public QueryFieldOperation elt(Object value) {
        if (value instanceof String) {
            where.append("<='").append(value).append("' ");
        } else {
            where.append("<=").append(value).append(" ");
        }
        return this;
    }

    public QueryFieldOperation egt(Object value) {
        if (value instanceof String) {
            where.append(">='").append(value).append("' ");
        } else {
            where.append(">=").append(value).append(" ");
        }
        return this;
    }

    public QueryFieldOperation like(String value) {
        where.append(" like ").append(value).append(" ");
        return this;
    }

    public QueryFieldOperation in(Collection<String> values) {
        StringBuffer sb = new StringBuffer();
        where.append(" in ( ");
        values.stream().forEach(s -> {
            sb.append("'").append(s).append("'").append(",");
        });
        where.append(sb.toString().substring(0, sb.length() - 1)).append(" ) ");
        return this;
    }

    public QueryFieldOperation sql(String sql) {
        where.append(sql).append(" ");
        return this;
    }

    public Page<Map<String, Object>> findByPage() {
        throw new UnsupportedOperationException();
    }
}
