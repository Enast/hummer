package org.enast.hummer.dynamodel.db.filed;

import org.enast.hummer.dynamodel.conmon.TimeUtils;
import org.enast.hummer.dynamodel.db.AbstractOperate;
import org.enast.hummer.dynamodel.db.DBOperation;
import org.enast.hummer.dynamodel.db.Field;
import org.enast.hummer.dynamodel.db.dao.DtbCommonDao;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhujinming6
 * @create 2020-03-17 18:54
 * @update 2020-03-17 18:54
 **/
public abstract class AbstractFieldOperation extends AbstractOperate {

    protected Map<String, Field> fields;
    protected List<Map<String, String>> values;
    protected String tableName;

    protected final static String toTimestamp = " to_timestamp(";
    protected final static String toDate = " to_date(";
    protected final static String YYYYMMDDSSXXX = " ,'yyyy-MM-dd HH24:MI:SS.MS')";
    protected final static String singleQuote = "'";
    protected final static String toNumber = " to_number(";

    public AbstractFieldOperation(DtbCommonDao dtbCommonDao, Map<String, Field> fields, List<Map<String, String>> values, String tableName, DBOperation operation) {
        super(dtbCommonDao, operation);
        this.fields = fields;
        this.tableName = tableName;
        this.values = values;
    }

    protected boolean filter(String f, Set<String> keys) {
        return !keys.contains(f);
    }

    protected Object getFieldValue(String value, Field field) {
        JDBCType dataType = field.getDbType();
        if (StringUtils.isBlank(value)) {
            if (dataType != null) {
                switch (dataType) {
                    case DATE:
                        return toDate + "null,null)";
                    case TIME:
                    case TIMESTAMP:
                    case TIME_WITH_TIMEZONE:
                    case TIMESTAMP_WITH_TIMEZONE:
                        return toTimestamp + "null,null)";
                    case BOOLEAN:
                        return "true";
                    case BIGINT:
                    case INTEGER:
                    case DECIMAL:
                    case TINYINT:
                    case NUMERIC:
                        return "0";
                    case FLOAT:
                    case DOUBLE:
                        return "0";
                    default:
                        return "null";
                }
            }
        } else {
            if (dataType != null) {
                switch (dataType) {
                    case TIME:
                        return toTimestamp + singleQuote + value + "','HH24:MI:SS.MS')";
                    case DATE:
                    case TIMESTAMP:
                    case TIME_WITH_TIMEZONE:
                    case TIMESTAMP_WITH_TIMEZONE:
                        return toTimestamp + singleQuote + TimeUtils.date2Str(TimeUtils.castToDate(value), TimeUtils.IOS8601_XXX) + singleQuote + YYYYMMDDSSXXX;
                    case BOOLEAN:
                        if (!("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value))) {
                            return "true";
                        }
                        return value;
                    case BIGINT:
                    case INTEGER:
                    case TINYINT:
                        return toNumber + singleQuote + value + singleQuote + ", '9999999999999999999')";
                    case DECIMAL:
                    case FLOAT:
                    case DOUBLE:
                    case NUMERIC:
                        return toNumber + singleQuote + value + singleQuote + ", '99999999.99')";
                    default:
                }
            }
        }
        return singleQuote + value + singleQuote;
    }

    public List<Map<String, Object>> query() {
        return null;
    }

    public Long count() {
        return 0L;
    }

}
