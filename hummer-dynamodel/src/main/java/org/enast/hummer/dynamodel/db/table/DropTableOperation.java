package org.enast.hummer.dynamodel.db.table;


import org.enast.hummer.dynamodel.conmon.CollectionUtils;
import org.enast.hummer.dynamodel.db.TableObject;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-13 14:57
 * @update 2020-03-13 14:57
 **/
public class DropTableOperation extends AbstractTableOperation {

    public DropTableOperation(DtbCommonDao dtbCommonDao, List<TableObject> tables) {
        super(dtbCommonDao, tables, true);
    }

    @Override
    public void before() {
        if (CollectionUtils.isEmpty(tables)) {
            return;
        }
        tables.stream().forEach(tableObject -> {buildSql(tableObject);});
    }

    private void buildSql(TableObject tableObject) {
        stringBuilder.append(" DROP TABLE ").append(tableObject.getTableName()).append(";");
    }
}
