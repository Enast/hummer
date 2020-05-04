package org.hummer.perfectmat.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.hummer.perfectmat.PafAccessData;
import org.hummer.perfectmat.ResourceCache;
import org.hummer.perfectmat.ResourceQuotas;

import java.util.List;

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
    void parseData(ResourceCache simpleVO, PafAccessData accessData, ResourceQuotas quota, String code);

    /**
     * 动态解析--报文code=0
     *
     * @param simpleVO
     * @param accessData
     * @param quotas
     */
    void custom(ResourceCache simpleVO, PafAccessData accessData, ResourceQuotas quotas, String code);

    /**
     * 动态解析--报文code!=0
     *
     * @param quotas
     * @param simpleVO
     * @param accessData
     */
    void customErrorCode(ResourceQuotas quotas, ResourceCache simpleVO, PafAccessData accessData, String code, JsonNode dataObject);

    /**
     * 特殊报文固定解析
     *
     * @param simpleVO
     * @param accessData
     * @param object
     */
    void specialByBusinessType(ResourceCache simpleVO, PafAccessData accessData, JsonNode object, ResourceQuotas quotas, String businessKey);

}
