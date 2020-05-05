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
public class UpdateFieldOperation extends AbstractFieldOperation {

    public UpdateFieldOperation(DtbCommonDao dtbCommonDao, Map<String, Field> fields, List<Map<String, String>> values, String tableName) {
        super(dtbCommonDao, fields, values, tableName, DBOperation.UPDATE);
    }

    @Override
    public void before() {
        List<Map<String, Object>> result = dtbCommonDao.query(columSql + tableName + columSqlEnd);
        Set<String> columns = result.stream().map(m -> (String) m.get("column_name")).collect(Collectors.toSet());

        stringBuilder.append(" update \"").append(tableName).append("\" set ");
        List<String> filedCodes = fields.keySet().stream().collect(Collectors.toList());
        List<String> newfiledCodes = new ArrayList<>();
        final String[] relationKey = {null};
        StringBuilder temp = new StringBuilder(" ) as tmp (");
        for (String field : filedCodes) {
            if (!columns.contains(field) || DynamicModelDefaultAttributes.id.getCode().equals(field) || DynamicModelDefaultAttributes.modifyTime.getCode().equals(field)) {
                continue;
            }
            stringBuilder.append("\"").append(field).append("\"=tmp.\"").append(field).append("\",");
            temp.append("\"").append(field).append("\",");
            newfiledCodes.add(field);
        }
        stringBuilder.append("\"").append(DynamicModelDefaultAttributes.modifyTime.getCode()).append("\"=tmp.\"").append(DynamicModelDefaultAttributes.modifyTime.getCode()).append("\" from (values ");
        fields.entrySet().stream().forEach(a -> {
            Field field = a.getValue();
            if (field.getRelationKey()) {
                relationKey[0] = field.getCode();
            }
        });
        int c = 0;
        for (Map<String, String> metric : values) {
            stringBuilder.append("(");
            for (String filed : newfiledCodes) {
                String value = metric.get(filed);
                stringBuilder.append(getFieldValue(value, fields.get(filed))).append(",");
            }
            Date dateCreate = null;
            String modifyTime = metric.get(DynamicModelDefaultAttributes.modifyTime.getCode());
            if (StringUtils.isNotBlank(modifyTime)) {
                Date date = TimeUtils.castToDate(modifyTime);
                dateCreate = date == null ? new Date() : date;
            } else {
                dateCreate = new Date();
            }
            stringBuilder.append(toTimestamp + singleQuote).append(TimeUtils.date2Str(dateCreate, TimeUtils.IOS8601_XXX)).append(singleQuote + YYYYMMDDSSXXX).append(")");
            c++;
            if (c < values.size()) {
                stringBuilder.append(",");
            }
        }
        temp.append("\"").append(DynamicModelDefaultAttributes.modifyTime.getCode()).append("\") where ").append(tableName).append(".\"").append(relationKey[0]).append("\"=tmp.\"").append(relationKey[0]).append("\"");
        stringBuilder.append(temp);
    }
}
