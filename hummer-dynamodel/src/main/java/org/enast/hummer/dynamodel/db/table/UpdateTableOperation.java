package org.enast.hummer.dynamodel.db.table;


import org.enast.hummer.dynamodel.conmon.CollectionUtils;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.TableObject;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhujinming6
 * @create 2020-03-13 14:04
 * @update 2020-03-13 14:04
 **/
public class UpdateTableOperation extends AbstractTableOperation {

    public UpdateTableOperation(DtbCommonDao dtbCommonDao, List<TableObject> tables, boolean cover) {
        super(dtbCommonDao, tables, cover);
    }

    @Override
    public void before() {
        if (CollectionUtils.isEmpty(tables)) {
            return;
        }
        tables.stream().forEach(tableObject -> {buildSql(tableObject);});
    }

    private void buildSql(TableObject tableObject) {
        List<Map<String, Object>> result = dtbCommonDao.query(columSql + tableObject.getTableName() + columSqlEnd);
        Set<String> columns = result.stream().map(m -> (String) m.get("column_name")).collect(Collectors.toSet());
        // 表不存在
        if (CollectionUtils.isEmpty(columns)) {
            List<TableObject> tables = new ArrayList<>();
            tables.add(tableObject);
            CreateTableOperation createTableOperation = new CreateTableOperation(this.dtbCommonDao, tables);
            createTableOperation.operate();
            return;
        }
        Set<Field> deleteFields = new HashSet<>();
        Set<Field> updateFields = new HashSet<>();
        Set<Field> addFields = new HashSet<>();
        tableObject.getFieldList().stream().filter(field -> field != null).forEach(field -> dealField(field, columns, updateFields, addFields));
        Map<String, Field> newColumns = tableObject.getFieldList().stream().collect(Collectors.toMap(Field::getCode, field -> field, (oldValue, newValue) -> oldValue));
        addFields.stream().forEach(field -> stringBuilder.append(field.getAddSql(tableObject.getTableName())));
        updateFields.stream().forEach(field -> stringBuilder.append(field.getAlterSql(tableObject.getTableName())));
        if (cover) {
            columns.stream().forEach(colum -> dealColums(colum, newColumns, deleteFields));
            deleteFields.stream().forEach(field -> stringBuilder.append(field.getDropSql(tableObject.getTableName())));
        }
    }

    private void dealColums(String column, Map<String, Field> newColumns, Set<Field> deleteFields) {
        Field field = newColumns.get(column);
        if (field == null) {
            field = new Field();
            field.setCode(column);
            deleteFields.add(field);
        }
    }

    private void dealField(Field field, Set<String> columns, Set<Field> updateFields, Set<Field> addFields) {
        boolean hasKey = false;
        for (String s : columns) {
            if (s.equals(field.getCode())) {
                hasKey = true;
                break;
            }
        }
        if (hasKey) {
            updateFields.add(field);
        } else {
            addFields.add(field);
        }
    }
}
