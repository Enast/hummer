package org.enast.hummer.perfectmat.converter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.aop.DataConverter;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author limengmeng7
 * @Description:
 * @date 2019-09-23 10:41
 */
@Component
@DataConverter(keys = "ioInfo")
public class IoInfoConverterImpl extends AbstractConverter {
    @Override
    public void analysis(ResourceCache simpleVO, JsonNode object, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        log.debug("ioInfoConverter");
        array2JsonStr("ioInfoList", object, status);
        computeAvg(object, "iopUpFlow", status);
        computeAvg(object, "iodownFlow", status);
    }
}
