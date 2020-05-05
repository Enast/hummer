package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhujinming6
 * @create 2020-03-12 18:59
 * @update 2020-03-12 18:59
 **/
@Log4j2
public abstract class AbstractOperate implements Operate {

    protected DBOperation operation;
    protected static String columSql = "select column_name from information_schema.columns where table_schema='public' and table_name = '";
    protected static String columSqlEnd = "';";

    protected DtbCommonDao dtbCommonDao;
    protected StringBuilder stringBuilder = new StringBuilder();

    public AbstractOperate(DtbCommonDao dtbCommonDao, DBOperation operation) {
        this.dtbCommonDao = dtbCommonDao;
    }

    public abstract void before();

    @Override
    public Object operate() {
        before();
        Object res = null;
        try {
            res = doOperation();
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return res;
    }

    private Object doOperation() {
        if (StringUtils.isBlank(stringBuilder)) {
            return 0;
        }
        log.info(stringBuilder.toString());
        return dtbCommonDao.runNativeSql(stringBuilder.toString(), operation);
    }

}
