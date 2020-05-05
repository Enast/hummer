/**
 * Copyright 2019 bejson.com
 */
package org.enast.hummer.perfectmat.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

/**
 * Auto-generated: 2019-11-20 10:55:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AccessData {

    private String code;
    private Integer cascade;
    private String typeErrorCode;
    private String indexCode;
    private String deviceTypeCode;
    private Integer dataType;
    private String businessType;
    private Date collectTime;
    private JsonNode data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Integer getCascade() {
        return cascade;
    }

    public void setCascade(Integer cascade) {
        this.cascade = cascade;
    }

    public void setTypeErrorCode(String typeErrorCode) {
        this.typeErrorCode = typeErrorCode;
    }

    public String getTypeErrorCode() {
        return typeErrorCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}