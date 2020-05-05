package org.enast.hummer.perfectmat.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.entity.ResourceCache;

/**
 * @author zhujinming6
 * @create 2018-05-29 15:46
 * @update 2018-05-29 15:46
 **/
public interface PafParseDataService {

    /**
     * 数据解析入口
     *
     * @param accessData
     */
    void parseData(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quota, String code);

    /**
     * 动态解析--报文code=0
     *
     * @param simpleVO
     * @param accessData
     * @param quotas
     */
    void custom(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quotas, String code);

    /**
     * 动态解析--报文code!=0
     *
     * @param quotas
     * @param simpleVO
     * @param accessData
     */
    void customErrorCode(ResourceQuotas quotas, ResourceCache simpleVO, AccessData accessData, String code, JsonNode dataObject);

    /**
     * 特殊报文固定解析
     *
     * @param simpleVO
     * @param accessData
     * @param object
     */
    void specialByBusinessType(ResourceCache simpleVO, AccessData accessData, JsonNode object, ResourceQuotas quotas, String businessKey);

}
