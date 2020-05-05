package org.enast.hummer.perfectmat.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.BusinessType;
import org.enast.hummer.perfectmat.entity.AccessData;

import java.util.Map;

/**
 * 错误码处理
 *
 * @author zhujinming6
 * @create 2018-07-11 18:56
 * @update 2018-07-11 18:56
 **/
public interface ErrorCodeService {

    /**
     * 正常时统一处理
     *
     * @param status, 保存状态属性
     *                code
     *                错误码
     * @return void
     * @author zhujinming6
     * @create_date 2018/7/11 18:58
     * @modify_date 2018/7/11 18:58
     */
    void normal(Map<String, Object> status);

    /**
     * 单一状态 探针 错误码处理
     *
     * @param status
     * @param code
     */
    void singleResource(Map<String, Object> status, String code, AccessData accessData, String businessType);

    /**
     * 单一状态 探针 错误码处理
     *
     * @param status
     * @param code
     */
    void singleCustomResource(String resourceType, Map<String, Object> status, String code, AccessData accessData, BusinessType businessType, JsonNode dataObject);


    /**
     * 根据snmp错误码处理在线状态
     *
     * @param status
     * @param code
     */
    boolean snmpErrorCode(Map<String, Object> status, String code, JsonNode jsonObject, String businessType);

    /**
     * 默认离线原因
     */
    void defaultOfflineReason(Map<String, Object> status, int val);

}
