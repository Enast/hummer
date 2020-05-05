package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.attribute.BaseAttribute;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.enast.hummer.dynamodel.db.table.CreateTableOperation;
import org.enast.hummer.dynamodel.db.table.DeleteTableFieldOperation;
import org.enast.hummer.dynamodel.db.table.DropTableOperation;
import org.enast.hummer.dynamodel.db.table.UpdateTableOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhujinming6
 * @create 2020-03-13 15:02
 * @update 2020-03-13 15:02
 **/
@Service
@ConditionalOnBean(DtbCommonDao.class)
public class TableOperateHandler implements TableOperate {


    @Autowired
    private DtbCommonDao dtbCommonDao;

    @Override
    public int crate(TableObject table) {
        return crate(addTable(table));
    }

    @Override
    public int drop(TableObject table) {
        return drop(addTable(table));
    }

    @Override
    public int deleteField(TableObject table) {
        return deleteField(addTable(table));
    }

    @Override
    public int update(TableObject table) {
        return update(addTable(table));
    }

    @Override
    public int crate(List<TableObject> tables) {
        checkArgs(tables);
        return (int) new CreateTableOperation(dtbCommonDao, tables).operate();
    }

    @Override
    public int drop(List<TableObject> tables) {
        checkArgs(tables);
        return (int) new DropTableOperation(dtbCommonDao, tables).operate();
    }

    @Override
    public int deleteField(List<TableObject> tables) {
        checkArgs(tables);
        return (int) new DeleteTableFieldOperation(dtbCommonDao, tables).operate();
    }

    @Override
    public int update(boolean cover, List<TableObject> tables) {
        checkArgs(tables);
        return (int) new UpdateTableOperation(dtbCommonDao, tables, cover).operate();
    }

    @Override
    public int update(List<TableObject> tables) {
        checkArgs(tables);
        return (int) new UpdateTableOperation(dtbCommonDao, tables, false).operate();
    }

    @Override
    public int updateAttribute(BaseAttribute attribute, String tableName) {
        List<TableObject> tableObjectList = new ArrayList<>();
        TableObject table = new TableObject();
        table.setTableName(tableName);
        Set<Field> fieldList = new HashSet<>();
        // 填充属性
        Field field = new Field();
        field.setCode(attribute.getIdentify());
        field.setLength(attribute.getDataLength());
        field.setPrimaryKey(attribute.getUnique());
        field.setType(attribute.getDataType());
        fieldList.add(field);
        table.setFieldList(fieldList);
        tableObjectList.add(table);
        return update(tableObjectList);
    }

    @Override
    public int deleteAttribute(BaseAttribute attribute, String tableName) {
        List<TableObject> tableObjectList = new ArrayList<>();
        TableObject table = new TableObject();
        table.setTableName(tableName);
        Set<Field> fieldList = new HashSet<>();
        // 填充属性
        Field field = new Field();
        field.setCode(attribute.getIdentify());
        field.setType(attribute.getDataType());
        fieldList.add(field);
        table.setFieldList(fieldList);
        tableObjectList.add(table);
        return deleteField(tableObjectList);
    }

    @Override
    public int updateAttributes(List<BaseAttribute> attributes, String tableName) {
        List<TableObject> tableObjectList = new ArrayList<>();
        TableObject table = new TableObject();
        table.setTableName(tableName);
        Set<Field> fieldList = new HashSet<>();
        attributes.stream().forEach(attribute -> {
            Field field = new Field();
            field.setCode(attribute.getIdentify());
            field.setLength(attribute.getDataLength());
            field.setPrimaryKey(attribute.getUnique());
            field.setType(attribute.getDataType());
            fieldList.add(field);
        });
        table.setFieldList(fieldList);
        tableObjectList.add(table);
        return update(tableObjectList);
    }

    @Override
    public int deleteAttributes(List<BaseAttribute> attributes, String tableName) {
        List<TableObject> tableObjectList = new ArrayList<>();
        TableObject table = new TableObject();
        table.setTableName(tableName);
        Set<Field> fieldList = new HashSet<>();
        attributes.stream().forEach(attribute -> {
            Field field = new Field();
            field.setCode(attribute.getIdentify());
            field.setType(attribute.getDataType());
            fieldList.add(field);
        });
        table.setFieldList(fieldList);
        tableObjectList.add(table);
        return deleteField(tableObjectList);
    }

    private void checkArgs(List<TableObject> tables) {
        Assert.notNull(dtbCommonDao, "dtbCommonDao is null");
        Assert.notNull(tables, "tables is null");
    }

    private List<TableObject> addTable(TableObject table) {
        List<TableObject> tableObjectList = new ArrayList<>();
        tableObjectList.add(table);
        return tableObjectList;
    }
}
