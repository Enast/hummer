package org.enast.hummer.perfectmat.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.BusinessType;
import org.enast.hummer.perfectmat.util.ErrorCodeUtil;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.service.ErrorCodeService;
import org.enast.hummer.perfectmat.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 错误码统一处理
 *
 * @author zhujinming6
 * @create 2018-07-11 19:02
 * @update 2018-07-11 19:02
 **/
@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {

    private Logger log = LoggerFactory.getLogger(ErrorCodeServiceImpl.class);


    @Resource
    PropertiesService propertiesService;

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
    @Override
    public void normal(Map<String, Object> status) {
        ErrorCodeUtil.normal(status);
    }

    @Override
    public void singleResource(Map<String, Object> status, String code, AccessData accessData, String businessType) {
        ErrorCodeUtil.singleResource(status, code, accessData, businessType);
    }


    /**
     * 单一状态 探针 错误码处理
     *
     * @param status
     * @param code
     * @param accessData
     * @param businessType
     */
    @Override
    public void singleCustomResource(String resourceType, Map<String, Object> status, String code, AccessData accessData, BusinessType businessType, JsonNode dataObject) {
        ErrorCodeUtil.singleCustomResource(resourceType, status, code, accessData, businessType, dataObject);
    }

    /**
     * snmp  错误码处理
     *
     * @param status
     * @param code
     * @param jsonObject
     */
    @Override
    public boolean snmpErrorCode(Map<String, Object> status, String code, JsonNode jsonObject, String businessType) {
        return  false;
    }

    @Override
    public void defaultOfflineReason(Map<String, Object> status, int onlineStatus) {
    }

}
