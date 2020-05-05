package org.enast.hummer.dynamodel.db.filed;

import org.enast.hummer.dynamodel.db.DBOperation;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.enast.hummer.dynamodel.db.AbstractOperate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * delete from test using (values (3),(4),(5)) as tmp(id) where test.id=tmp.id;
 *
 * @author zhujinming6
 * @create 2020-03-17 18:52
 * @update 2020-03-17 18:52
 **/
public class DeleteFieldOperation extends AbstractFieldOperation {

    List<String> deleteIds;
    Field field;

    public DeleteFieldOperation(DtbCommonDao dtbCommonDao, Field field, List<String> deleteIds, String tableName) {
        super(dtbCommonDao, null, null, tableName, DBOperation.DELETE);
        this.deleteIds = deleteIds;
        this.field = field;
    }

    @Override
    public void before() {
        List<Map<String, Object>> result = dtbCommonDao.query(AbstractOperate.columSql + tableName + AbstractOperate.columSqlEnd);
        Set<String> columns = result.stream().map(m -> (String) m.get("column_name")).collect(Collectors.toSet());
        if (!columns.contains(field.getCode())) {
            return;
        }
        stringBuilder.append(" delete from \"").append(tableName).append("\" using ( values ");
        StringBuilder temp = new StringBuilder(" ) as tmp (\"").append(field.getCode()).append("\"");
        int c = 0;
        for (String id : deleteIds) {
            stringBuilder.append("('").append(id).append("')");
            c++;
            if (c < deleteIds.size()) {
                stringBuilder.append(",");
            }
        }
        temp.append(") where ").append(tableName).append(".\"").append(field.getCode()).append("\"=tmp.\"").append(field.getCode()).append("\";");
        stringBuilder.append(temp);
    }
}
