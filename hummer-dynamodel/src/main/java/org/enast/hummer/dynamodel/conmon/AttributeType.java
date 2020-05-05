package org.enast.hummer.dynamodel.conmon;

/**
 * 属性类型枚举
 * @author weijunhua
 * 2017年11月30日
 */
public enum AttributeType {
    /**
     * 静态属性
     */
    STATIC("STATIC", "静态属性"),
    /**
     * 状态属性
     */
    STATUS("STATUS", "状态属性"),
    /**
     * 全部
     */
    ALL("ALL", "2边都存在"),
    /**
     * 隐藏属性，只用于代码中，数据库和页面不使用
     */
    HIDDEN("HIDDEN", "隐藏属性"),
    /** 性能属性 */
    PERFORM("PERFORM", "性能属性");

    AttributeType() {
    }

    AttributeType(String code) {
        this.code = code;
    }

    private AttributeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AttributeType getByText(String text) {
        try {
            return AttributeType.valueOf(text);
        } catch (Exception e) {
            return STATIC;
        }
    }
}
