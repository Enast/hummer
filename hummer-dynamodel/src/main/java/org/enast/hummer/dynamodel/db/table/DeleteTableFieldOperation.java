package org.enast.hummer.dynamodel.db.table;

import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.TableObject;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhujinming6
 * @create 2020-04-29 10:45
 * @update 2020-04-29 10:45
 **/
public class DeleteTableFieldOperation extends AbstractTableOperation {

    public DeleteTableFieldOperation(DtbCommonDao dtbCommonDao, List<TableObject> tableObjects) {
        super(dtbCommonDao, tableObjects, false);
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
            return;
        }
        Set<Field> deleteFields = tableObject.getFieldList();
        deleteFields.stream().forEach(field -> stringBuilder.append(field.getDropSql(tableObject.getTableName())));
    }
}
