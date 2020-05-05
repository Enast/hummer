package org.enast.hummer.perfectmat.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.enast.hummer.perfectmat.entity.AccessData;
import org.enast.hummer.perfectmat.entity.ResourceCache;

import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-21 11:44
 * @update 2019-09-21 11:44
 **/
public interface BasicConverter {

    /**
     * 特殊报文解析入口
     * 统一处理错误码和其他扩展方法
     *
     * @param simpleVO     资源缓存信息
     * @param accessData   报文原始对象
     * @param dataObject   报文data的对象
     * @param businessType 能力集类型
     */
    void analysisSpecial(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quotas, JsonNode dataObject, String businessType);

    /**
     * 统一错误处理
     *
     * @param simpleVO
     * @param quotas
     * @param accessData
     * @param code
     * @param businessType
     */
    void dealError(ResourceCache simpleVO, ResourceQuotas quotas, AccessData accessData, String code, String businessType, JsonNode dataObject);

    /**
     * 转换器和解析必需实现的方法
     *
     * @param simpleVO
     * @param accessData
     * @param quota
     * @param dataObject
     * @param businessType
     */
    void analysis(ResourceCache simpleVO, AccessData accessData, ResourceQuotas quota, JsonNode dataObject, String businessType, String code);

    /**
     * 转换器和解析必需实现的方法
     *
     * @param simpleVO
     * @param status
     * @param dataObject
     * @param businessType
     */
    // 一切都是为了性能,为了性能的一切
    void analysis(ResourceCache simpleVO, JsonNode jsonNode, Map<String, Object> status, JsonNode dataObject, String businessType, String code);


    /**
     * 巡检正常的情况下,必检项,没有检测到设置为-1
     *
     * @param status
     */
    void resetStatus(Map<String, Object> status);
}
