package org.enast.hummer.dynamodel.db.table;

import org.enast.hummer.dynamodel.conmon.CollectionUtils;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.TableObject;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-12 18:58
 * @update 2020-03-12 18:58
 **/
public class CreateTableOperation extends AbstractTableOperation {

    public CreateTableOperation(DtbCommonDao dtbCommonDao, List<TableObject> tables) {
        super(dtbCommonDao, tables, true);
    }

    @Override
    public void before() {
        if (CollectionUtils.isEmpty(tables)) {
            return;
        }
        tables.stream().forEach(tableObject -> {buildSql(tableObject);});
    }

    private void buildSql(TableObject table) {
        stringBuilder.append(" create ").append(table.isUnloggedTable() ? " unlogged " : "").append(" table \"").append(table.getTableName()).append("\"(");
        boolean f = false;
        boolean hasPrimaryKey = false;
        String keyCode = null;
        for (Field field : table.getFieldList()) {
            stringBuilder.append(f ? "," : "");
            stringBuilder.append(field.getInitSql());
            if (field.getPrimaryKey() != null && field.getPrimaryKey()) {
                stringBuilder.append(" primary key");
                hasPrimaryKey = true;
                keyCode = field.getCode();
            }
            f = true;
        }
        stringBuilder.append(table.getFillfactor() >= 10 && table.getFillfactor() <= 100 ? ")with (fillfactor=" + table.getFillfactor() + ");" : ");");
        if (hasPrimaryKey) {
            stringBuilder.append(" CREATE SEQUENCE IF not exists \"").append(table.getTableName()).append("_id_seq\" START WITH 1  INCREMENT BY 1  NO MINVALUE NO MAXVALUE CACHE 1;").append("alter table \"").append(table.getTableName()).append("\" alter column \"").append(keyCode).append("\" set default nextval('").append(table.getTableName()).append("_id_seq');");
        }
    }
}
