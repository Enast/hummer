package org.enast.hummer.dynamodel.db;

import org.enast.hummer.dynamodel.conmon.DataType;
import org.enast.hummer.dynamodel.conmon.PostgresqlType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;

/**
 * @author zhujinming6
 * @create 2020-03-12 19:15
 * @update 2020-03-12 19:15
 **/
@Data
@EqualsAndHashCode(of = {"code"})
public class Field {

    String comment;
    String code;
    DataType type;
    /**
     * 长度
     */
    Integer length = 32;
    /**
     * 精度
     */
    Integer scale;
    /**
     * 是否加密
     */
    boolean encrypt = false;

    Boolean primaryKey = false;
    Boolean relationKey = false;

    private Boolean notNull;
    private String defaultValue;

    /**
     * 校验字段参数合法性
     */
    private final boolean check() {
        //校验编码是否合法
        //        String codeRegex = "^[a-zA-Z0-9_]{1,128}$";
        //        if (!code.matches(codeRegex)) {
        //            throw new DtbException(DtbResultCode.DTB_ATTRIBUTE_CODE_INVALID);
        //        }
        //        if (type == DataType.FLOAT && scale > length) {
        //            throw new DtbException(DtbResultCode.DTB_ATTRIBUTE_LENGTH_INVALID);
        //        }
        return true;
    }

    /**
     * 获取字段描述sql语句
     * 如：name varchar(64);birthday date;salary numeric(12, 2);
     *
     * @return
     */
    private String getDefinateSql() {
        check();
        StringBuilder ret = new StringBuilder();
        switch (type) {
            case VARCHAR: {
                //如果是加密字段或长度大于等于1000，则为text类型
                if ((encrypt == true) || (length != null && length >= 1000)) {
                    ret.append(PostgresqlType.getSqlType(type.getExtDbType()));
                } else {
                    ret.append(PostgresqlType.getSqlType(type.getDbType())).append("(").append(length == null ? 512 : length).append(")");
                }
                break;
            }
            case FLOAT:
                ret.append(PostgresqlType.getSqlType(type.getDbType())).append("(").append(length == null ? 64 : length).append(",").append(scale == null ? 2 : scale).append(")");
                break;
            case DICT:
            case IMAGE:
            case REFERENCE:
                ret.append(PostgresqlType.getSqlType(type.getDbType())).append("(").append(type.getDefaultLen()).append(")");
                break;
            default:
                ret.append(type.getDbType());
        }
        setDefaultValueAndNull(ret);
        return ret.toString();
    }

    private void setDefaultValueAndNull(StringBuilder ret) {
        if (notNull != null && notNull) {
            ret.append(" NOT NULL ");
        }
        if (StringUtils.isNotBlank(defaultValue)) {
            ret.append(" DEFAULT ").append(defaultValue);
        }
    }

    public final String getInitSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("\"").append(code).append("\" ");
        sql.append(getDefinateSql());
        //		if (this == ASSETID || this == ID) {
        //			sql.append(" primary key");
        //		}
        return sql.toString();
    }

    public final String getAddSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("alter table \"").append(tableName).append("\" add column if not exists \"").append(code).append("\" ").append(getDefinateSql()).append(";");
        return sql.toString();
    }

    public final String getAlterSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("alter table \"").append(tableName).append("\" alter column \"").append(code).append("\" type ").append(getDefinateSql()).append(";");
        return sql.toString();
    }

    public final String getDropSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("alter table \"").append(tableName).append("\" drop column if exists \"").append(code).append("\";");
        return sql.toString();
    }

    /**
     * 获取属性对应的数据库类型
     *
     * @return
     */
    public final JDBCType getDbType() {
        if (type == DataType.VARCHAR && (encrypt == true || length >= 1000)) {
            return type.getExtDbType();
        } else {
            return type.getDbType();
        }
    }


}
