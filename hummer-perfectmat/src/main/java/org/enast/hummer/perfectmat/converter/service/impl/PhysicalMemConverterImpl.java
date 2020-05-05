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
@DataConverter(keys = "physicalMem")
public class PhysicalMemConverterImpl extends AbstractConverter {

    /**
     * 服务器虚拟内存
     *
     * @param simpleVO
     * @param jsonNode
     * @param status
     */
    @Override
    public void analysis(ResourceCache simpleVO, JsonNode jsonNode, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        log.debug("physicalMemConverter");
        if (jsonNode == null) {
            return;
        }
        Long total = JsonNodeUtil.getLong(jsonNode, ServerAccess.physicalMemTotal.getOriginCode());
        if (total != null) {
            status.put(ServerAccess.physicalMemTotal.getHospCode(), total / (1024 * 1024f));
        }
        Long totalUsed = JsonNodeUtil.getLong(jsonNode, ServerAccess.physicalMemUsed.getOriginCode());
        if (totalUsed != null) {
            status.put(ServerAccess.physicalMemUsed.getHospCode(), totalUsed / (1024 * 1024f));
        }
        if (total != null && totalUsed != null && total.intValue() != 0) {
            computeUsageDouble(totalUsed, total, ServerAccess.physicalMemUsage.getHospCode(), status);
        } else {
            log.debug(" physicalMemConverter total :{}", total);
        }
    }
}
