package org.enast.hummer.perfectmat.entity;

import org.enast.hummer.perfectmat.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 探针上传的报文描述
 *
 * @auther shixiafeng
 * @create 2019-10-08
 */
public class ResourceQuotas implements Comparable<ResourceQuotas> {

    private final static Logger log = LoggerFactory.getLogger(ResourceQuotas.class);

    /**
     * 资源id
     */
    private String resId;
    private String indexCode;

    private String typeCode;
    /**
     * 业务类型
     */
    private BusinessType businessType;

    /**
     * 采集时间
     */
    private Date collectTime;

    private String collectTimeLocal;
    private String collectTimeDiff;

    /**
     * 指标
     */
    private Map<String, Object> quotas;


    ResourceCache vo;


    public ResourceQuotas() {
    }

    public ResourceQuotas(ResourceCache simpleVO, BusinessType businessType, Date collectTime, Map<String, Object> quotas) {
        this.resId = simpleVO.getResId();
        this.indexCode = simpleVO.getIndexCode();
        this.businessType = businessType;
        this.collectTime = collectTime;
        this.quotas = quotas;
        PropertiesUtil.fillDstTime(collectTime, this);
    }

    public ResourceQuotas(ResourceCache simpleVO, String resourceType, BusinessType businessType, Date collectTime, Map<String, Object> quotas) {
        this.resId = simpleVO.getResId();
        this.indexCode = simpleVO.getIndexCode();
        this.typeCode = resourceType;
        this.businessType = businessType;
        this.collectTime = collectTime;
        this.quotas = quotas;
        PropertiesUtil.fillDstTime(collectTime, this);
    }

    public ResourceQuotas(String resId, String indexCode, String resourceType, BusinessType businessType, Date collectTime, Map<String, Object> quotas) {
        this.resId = resId;
        this.indexCode = indexCode;
        this.typeCode = resourceType;
        this.businessType = businessType;
        this.collectTime = collectTime;
        this.quotas = quotas;
        PropertiesUtil.fillDstTime(collectTime, this);
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Map<String, Object> getQuotas() {
        return quotas;
    }

    public void setQuotas(Map<String, Object> quotas) {
        this.quotas = quotas;
    }

    public String getCollectTimeLocal() {
        return collectTimeLocal;
    }

    public void setCollectTimeLocal(String collectTimeLocal) {
        this.collectTimeLocal = collectTimeLocal;
    }

    public String getCollectTimeDiff() {
        return collectTimeDiff;
    }

    public void setCollectTimeDiff(String collectTimeDiff) {
        this.collectTimeDiff = collectTimeDiff;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    /**
     * 升序排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(ResourceQuotas o) {
        ResourceQuotas other = (ResourceQuotas) o;
        long diff = collectTime.getTime() - other.collectTime.getTime();
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
