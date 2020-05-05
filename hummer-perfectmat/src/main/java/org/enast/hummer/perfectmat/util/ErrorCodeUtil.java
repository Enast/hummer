package org.enast.hummer.perfectmat.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.BusinessType;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 错误码统一处理(工具类)
 *
 * @author zhujinming6
 * @create 2018-07-11 19:02
 * @update 2018-07-11 19:02
 **/
public class ErrorCodeUtil {

    private static Logger log = LoggerFactory.getLogger(ErrorCodeUtil.class);


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
    public static void normal(Map<String, Object> status) {

    }



    public static void singleResource(Map<String, Object> status, String code, AccessData accessData, String businessType) {
        String typeErrorCode = accessData.getTypeErrorCode();
        errorCode(status, typeErrorCode, code, businessType);
        String resourceType = accessData.getDeviceTypeCode();
    }


    /**
     * 单一状态 探针 错误码处理
     *
     * @param resourceType
     * @param status
     * @param code
     * @param accessData
     * @param businessType
     */
    public static void singleCustomResource(String resourceType, Map<String, Object> status, String code, AccessData accessData, BusinessType businessType, JsonNode dataObject) {
        String typeErrorCode = accessData.getTypeErrorCode();
        errorCode(status, typeErrorCode, code, businessType.getCode());
    }



    public static void errorCode(Map<String, Object> status, String typeErrorCode, String code, String businessType) {

    }

    public static void errorCodeDeFault(Map<String, Object> status, String businessType) {

    }

    public static void errorCodeNormal(Map<String, Object> status, String businessType) {

    }

}
