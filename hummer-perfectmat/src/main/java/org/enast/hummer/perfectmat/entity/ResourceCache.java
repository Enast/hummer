package org.enast.hummer.perfectmat.entity;

public class ResourceCache {

    /**
     * 资源id
     */
    private String resId;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 国标编码
     */
    private String indexCode;

    private String subTypeCode;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getSubTypeCode() {
        return subTypeCode;
    }

    public void setSubTypeCode(String subTypeCode) {
        this.subTypeCode = subTypeCode;
    }
}
