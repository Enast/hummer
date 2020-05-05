package org.enast.hummer.dynamodel.db.table;

import org.enast.hummer.dynamodel.db.AbstractOperate;
import org.enast.hummer.dynamodel.db.DBOperation;
import org.enast.hummer.dynamodel.db.TableObject;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-17 18:54
 * @update 2020-03-17 18:54
 **/
public abstract class AbstractTableOperation extends AbstractOperate {

    protected List<TableObject> tables;
    protected boolean cover;

    public AbstractTableOperation(DtbCommonDao dtbCommonDao, List<TableObject> tables, boolean cover) {
        super(dtbCommonDao, DBOperation.UPDATE);
        this.tables = tables;
        this.cover = cover;
    }
}
