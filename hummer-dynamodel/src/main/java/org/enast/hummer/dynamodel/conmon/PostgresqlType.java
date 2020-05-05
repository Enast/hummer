package org.enast.hummer.dynamodel.conmon;
import java.sql.JDBCType;

/**
 * 获取pg类型
 *
 * @auther shixiafeng
 * @create 2019-10-17
 */
public class PostgresqlType {
    public static String getSqlType(JDBCType type) {
        switch (type) {
            case BOOLEAN:
                return "bool";
            case INTEGER:
                return "int4";
            case BIGINT:
                return "int8";
            case NUMERIC:
                return "numeric";
            case VARCHAR:
                return "varchar";
            case CLOB:
                return "text";
            case BLOB:
                return "bytea";
            case TIME:
                return "time";
            case TIMESTAMP:
                return "timestamp";
            case DATE:
                return "date";
            case DECIMAL:
                return "decimal";
            default:
                return null;
        }
    }
}
