package org.enast.hummer.dynamodel.conmon;


import java.sql.JDBCType;

public enum DataType {
    BOOL("0", JDBCType.BOOLEAN, null, 0, "布尔型"),

    VARCHAR("1", JDBCType.VARCHAR, JDBCType.CLOB, 0, "文本"),

    INT("2", JDBCType.INTEGER, null, 0, "整数"),

    FLOAT("3", JDBCType.NUMERIC, null, 0, "小数"),

    TIMESTAMP("4", JDBCType.TIMESTAMP, null, 0, "时间戳"),

    TIME("5", JDBCType.TIME, null, 0, "时间"),

    DICT("6", JDBCType.VARCHAR, JDBCType.CLOB, 1024, "字典"),

    IMAGE("7", JDBCType.VARCHAR, null, 255, "图片"),

    REFERENCE("8", JDBCType.VARCHAR, null, 255, "引用类型"),

    CLOB("9", JDBCType.CLOB, null, 0, "文本"),

    LONG("10", JDBCType.BIGINT, null, 0, "大整数"),

    DATE("11", JDBCType.DATE, null, 0, "日期到天"),

    DOUBLE("12", JDBCType.NUMERIC, null, 0, "小数");

    private DataType(String code, JDBCType dbType, JDBCType extDbType, int defaultLen, String desc) {
        this.code = code;
        this.dbType = dbType;
        this.extDbType = extDbType;
        this.defaultLen = defaultLen;
        this.desc = desc;
    }

    private String code;
    private JDBCType dbType;
    private JDBCType extDbType;
    private int defaultLen;
    private String desc;

    public String getCode() {
        return code;
    }

    public JDBCType getDbType() {
        return dbType;
    }

    public JDBCType getExtDbType() {
        return extDbType;
    }

    public int getDefaultLen() {
        return defaultLen;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean containsCode(String code) {
        for (DataType t : DataType.values()) {
            if (t.code.equals(code))
                return true;
        }
        return false;
    }

    public static DataType getByCode(String code) {
        for (DataType t : DataType.values()) {
            if (t.code.equals(code))
                return t;
        }
        throw new RuntimeException();
    }

    public static DataType getByText(String text) {
        try {
            return valueOf(text);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    //    public DataTypeDTO buildDTO() {
    //        switch (this) {
    //            case TIME:
    //                return DataTypeDTO.TIME;
    //            case DATE:
    //                return DataTypeDTO.DATE;
    //            case VARCHAR:
    //                return DataTypeDTO.VARCHAR;
    //            case BOOL:
    //                return DataTypeDTO.BOOL;
    //            case INT:
    //                return DataTypeDTO.INT;
    //            case FLOAT:
    //                return DataTypeDTO.FLOAT;
    //            case REFERENCE:
    //                return DataTypeDTO.REFERENCE;
    //            case DICT:
    //                return DataTypeDTO.DICT;
    //            case IMAGE:
    //                return DataTypeDTO.IMAGE;
    //            default:
    //                return DataTypeDTO.UNKNOWN;
    //
    //        }
    //    }
}
