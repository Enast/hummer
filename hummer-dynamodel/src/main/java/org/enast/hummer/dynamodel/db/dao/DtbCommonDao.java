package org.enast.hummer.dynamodel.db.dao;

import org.enast.hummer.dynamodel.db.DBOperation;

import java.util.List;
import java.util.Map;

public interface DtbCommonDao {
    int runNativeSql(String sql, DBOperation operation);

    List<Map<String, Object>> query(String sql);

    void excute(String sql, Object... parameters);
}
