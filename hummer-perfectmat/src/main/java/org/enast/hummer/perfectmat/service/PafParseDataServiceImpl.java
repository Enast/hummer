package org.enast.hummer.perfectmat.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.enast.hummer.perfectmat.*;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.enast.hummer.perfectmat.converter.ConvertersUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * @author zhujinming6
 * @create 2018-05-29 15:47
 * @update 2018-05-29 15:47
 **/
@Service
public class PafParseDataServiceImpl implements PafParseDataService {

    private Logger log = LoggerFactory.getLogger(PafParseDataServiceImpl.class);

    @Autowired
    PropertiesService propertiesService;
    @Resource
    ErrorCodeService errorCodeService;

    /**
     * 探针上报数据有效性
     */
    public static final String NORMAL = "0";
    public static final String UNDER_LINE = "_";

    /**
     * 数据解析入口
     *
     * @param accessData
     */
    @Override
    public void parseData(ResourceCache resourceCache, PafAccessData accessData, ResourceQuotas quota, String code) {
        custom(resourceCache, accessData,quota,code);
        log.info("status,json:{}", JSON.toJSONString(quota.getQuotas()));
        // 调用cmdb接口入库
        propertiesService.sendStatus(quota,resourceCache);
        log.info("pas message,typeCode:{},business:{}", quota.getTypeCode(), quota.getBusinessType().getCode());
    }

    /**
     * 动态解析--报文code=0
     *
     * @param accessData
     * @param quotas
     */
    @Override
    public void custom(ResourceCache simpleVO, PafAccessData accessData, ResourceQuotas quotas, String code) {
        // 1.特殊处理的报文,不使用通用解析模块,包括 @specialBusinessType
        // 特殊报文(根据资源类型+能力类型/资源类型/能力类型)固定解析
        // 优先级:源类型+能力类型>资源类型>能力类型
        boolean specialDeal = false;
        String businessKey = null;
        if (ConvertersUtils.specialConverters.containsKey(quotas.getTypeCode() + UNDER_LINE + quotas.getBusinessType().getCode())) {
            specialDeal = true;
            businessKey = quotas.getTypeCode() + UNDER_LINE + quotas.getBusinessType().getCode();
        } else if (ConvertersUtils.specialConverters.containsKey(quotas.getTypeCode())) {
            specialDeal = true;
            businessKey = quotas.getTypeCode();
        } else if (ConvertersUtils.specialConverters.containsKey(quotas.getBusinessType().getCode())) {
            specialDeal = true;
            businessKey = quotas.getBusinessType().getCode();
        }
        JsonNode dataObject = accessData.getData();
        if (NORMAL.equals(code)) {
            // 巡检正常重置错误码
            ErrorCodeUtil.errorCodeNormal(quotas.getQuotas(), quotas.getBusinessType().getCode());
        } else {
            if (!specialDeal) {
                customErrorCode(quotas, simpleVO, accessData, code, dataObject);
            }
        }
        if (dataObject == null) {
            return;
        }
        if (specialDeal) {
            specialByBusinessType(simpleVO, accessData, dataObject, quotas, businessKey);
            return;
        }
        // 2.通用解析模块
        Iterator<String> iterator = dataObject.fieldNames();
        while (iterator.hasNext()) {
            String key = iterator.next();
            // 过滤调无效属性
            if (PafConstant.INDEX_CODE.equals(key)) {
                continue;
            }
            JsonNode dataNode = dataObject.get(key);
            if (dataNode instanceof ArrayNode) {
                AbstractConverter converter = ConvertersUtils.getCustomConverter(key);
                if (converter != null) {
                    converter.analysis(simpleVO, dataNode, quotas.getQuotas(), dataObject, quotas.getBusinessType().getCode(), code);
                }
            } else if (dataNode instanceof ObjectNode) {
                AbstractConverter converter = ConvertersUtils.getCustomConverter(key);
                if (converter != null) {
                    converter.analysis(simpleVO, dataNode, quotas.getQuotas(), dataObject, quotas.getBusinessType().getCode(), code);
                }
            } else {
                quotas.getQuotas().put(key, JsonNodeUtil.getObject(dataNode));
            }
        }
    }

    /**
     * 特殊报文(根据资源类型+能力类型/资源类型/能力类型)固定解析
     * 优先级:源类型+能力类型>资源类型>能力类型
     *
     * @param simpleVO
     * @param accessData
     * @param quotas
     * @param businessKey
     */
    @Override
    public void specialByBusinessType(ResourceCache simpleVO, PafAccessData accessData, JsonNode dataObject, ResourceQuotas quotas, String businessKey) {
        AbstractConverter converter = ConvertersUtils.getSpecialConverter(businessKey);
        if (converter != null) {
            converter.analysisSpecial(simpleVO, accessData, quotas, dataObject, quotas.getBusinessType().getCode());
        }
    }

    /**
     * 动态解析--报文code!=0
     *
     * @param quotas
     * @param simpleVO
     * @param accessData
     * @param code
     */
    @Override
    public void customErrorCode(ResourceQuotas quotas, ResourceCache simpleVO, PafAccessData accessData, String code, JsonNode dataObject) {
        PafBusinessType pafBusinessType = quotas.getBusinessType();
        if (pafBusinessType == null) {
            return;
        }
        errorCodeService.singleCustomResource(quotas.getTypeCode(), quotas.getQuotas(), code, accessData, pafBusinessType, dataObject);
    }

}
