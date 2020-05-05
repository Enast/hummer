package org.enast.hummer.perfectmat.converter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.ServerAccess;
import org.enast.hummer.perfectmat.util.JsonNodeUtil;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.aop.DataConverter;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-23 14:32
 * @update 2019-09-23 14:32
 **/
@Component
@DataConverter(keys = "cpuList")
public class CpuListConverterImpl extends AbstractConverter {

    /**
     * 服务器cpu列表存json
     * 和平均使用率
     *
     * @param simpleVO
     * @param object
     * @param status
     */
    @Override
    public void analysis(ResourceCache simpleVO, JsonNode object, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        log.debug("cpuListConverter");
        if (JsonNodeUtil.isEmptyArray(object)) {
            return;
        }
        array2JsonStr(ServerAccess.cpuList.getHospCode(), object, status);
        computeSignleAvg(object, ServerAccess.cpuUsage.getHospCode(), status);
    }
}
