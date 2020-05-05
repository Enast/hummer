package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.attribute.BaseAttribute;

import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-12 18:50
 * @update 2020-03-12 18:50
 **/
public interface TableOperate {

    int crate(TableObject table);

    int drop(TableObject tables);

    int deleteField(TableObject table);

    int deleteField(List<TableObject> tables);

    int update(TableObject tables);

    int crate(List<TableObject> tables);

    int drop(List<TableObject> tables);

    int update(boolean cover, List<TableObject> tables);

    int update(List<TableObject> tables);

    int updateAttribute(BaseAttribute attribute, String tableName);

    int updateAttributes(List<BaseAttribute> attributes, String tableName);

    int deleteAttribute(BaseAttribute attribute, String tableName);

    int deleteAttributes(List<BaseAttribute> attributes, String tableName);
}
