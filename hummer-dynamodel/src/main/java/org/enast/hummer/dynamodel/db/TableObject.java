package org.enast.hummer.dynamodel.db;

import lombok.Data;

import java.util.Set;

/**
 * @author zhujinming6
 * @create 2020-03-13 13:52
 * @update 2020-03-13 13:52
 **/
@Data
public class TableObject {

    private String tableName;
    /**
     * fillfactor会降低insert的性能,但是update和delete性能将有提升
     */
    private int fillfactor;
    /**
     * 是否创建临时表
     */
    private boolean unloggedTable;

    private Set<Field> fieldList;
}
