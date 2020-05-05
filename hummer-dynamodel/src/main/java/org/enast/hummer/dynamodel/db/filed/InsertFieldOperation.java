package org.enast.hummer.dynamodel.db.filed;


import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.dynamodel.conmon.DynamicModelDefaultAttributes;
import org.enast.hummer.dynamodel.conmon.TimeUtils;
import org.enast.hummer.dynamodel.db.DBOperation;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhujinming6
 * @create 2020-03-17 18:52
 * @update 2020-03-17 18:52
 **/
public class InsertFieldOperation extends AbstractFieldOperation {


    public InsertFieldOperation(DtbCommonDao dtbCommonDao, Map<String, Field> fields, List<Map<String, String>> values, String tableName) {
        super(dtbCommonDao, fields, values, tableName, DBOperation.INSERT);
    }

    /**
     * 批量新增数据
     */
    @Override
    public void before() {
        List<Map<String, Object>> result = dtbCommonDao.query(columSql + tableName + columSqlEnd);
        Set<String> columns = result.stream().map(m -> (String) m.get("column_name")).collect(Collectors.toSet());

        stringBuilder.append(" INSERT INTO \"").append(tableName).append("\"").append(" ( ");
        List<String> filedCodes = fields.keySet().stream().collect(Collectors.toList());
        List<String> newfiledCodes = new ArrayList<>();
        for (String field : filedCodes) {
            if (!columns.contains(field) || DynamicModelDefaultAttributes.id.getCode().equals(field) || DynamicModelDefaultAttributes.createTime.getCode().equals(field) || DynamicModelDefaultAttributes.modifyTime.getCode().equals(field)) {
                continue;
            }
            stringBuilder.append("\"").append(field).append("\",");
            newfiledCodes.add(field);
        }
        stringBuilder.append("\"").append(DynamicModelDefaultAttributes.createTime.getCode()).append("\",");
        stringBuilder.append("\"").append(DynamicModelDefaultAttributes.modifyTime.getCode()).append("\")");
        stringBuilder.append(" VALUES ");
        int c = 0;
        for (Map<String, String> metric : values) {
            stringBuilder.append("(");
            for (String filed : newfiledCodes) {
                String value = metric.get(filed);
                if (value == null) {
                    stringBuilder.append("null,");
                } else {
                    Object o = getFieldValue(value, fields.get(filed));
                    if (o == null) {
                        stringBuilder.append("null,");
                    } else {
                        stringBuilder.append(o).append(",");
                    }
                }
            }
            Date dateCreate = null;
            Date dateModify = null;
            String createTime = metric.get(DynamicModelDefaultAttributes.createTime.getCode());
            String modifyTime = metric.get(DynamicModelDefaultAttributes.modifyTime.getCode());
            if (StringUtils.isNotBlank(createTime)) {
                Date date = TimeUtils.castToDate(createTime);
                dateCreate = date == null ? new Date() : date;
            } else {
                dateCreate = new Date();
            }
            stringBuilder.append(toTimestamp + singleQuote).append(TimeUtils.date2Str(dateCreate, TimeUtils.IOS8601_XXX)).append(singleQuote + YYYYMMDDSSXXX).append(",");
            if (StringUtils.isNotBlank(modifyTime)) {
                Date date = TimeUtils.castToDate(modifyTime);
                dateModify = date == null ? new Date() : date;
            } else {
                dateModify = new Date();
            }
            stringBuilder.append(toTimestamp + singleQuote).append(TimeUtils.date2Str(dateModify, TimeUtils.IOS8601_XXX)).append(singleQuote + YYYYMMDDSSXXX).append(")");
            c++;
            if (c < values.size()) {
                stringBuilder.append(",");
            }
        }
    }
}
