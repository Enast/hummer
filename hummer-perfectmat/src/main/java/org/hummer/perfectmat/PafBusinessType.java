package org.hummer.perfectmat;

import java.util.HashMap;

/**
 * 探针报文能力集
 *
 * @author zhujinming6
 * @create 2019-09-25 14:31
 * @update 2019-09-25 14:31
 **/
public enum PafBusinessType {

    /**
     * 基本信息
     */
    BASE_INFO("baseInfo"),
    /**
     * 基本状态
     */
    STATUS("status"),
    ;

    private final static HashMap<String, PafBusinessType> CODE_TO_ENUM;

    static {
        CODE_TO_ENUM = new HashMap<>();
        for (PafBusinessType type : PafBusinessType.values()) {
            CODE_TO_ENUM.put(type.getCode(), type);
        }
    }

    PafBusinessType(String code) {
        this.code = code;
    }

    public static PafBusinessType code4(String code) {
        PafBusinessType type = CODE_TO_ENUM.get(code);
        return type;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
