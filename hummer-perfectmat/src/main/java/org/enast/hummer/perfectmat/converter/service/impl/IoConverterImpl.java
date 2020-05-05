package org.enast.hummer.perfectmat.converter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.enast.hummer.perfectmat.entity.IOList;
import org.enast.hummer.perfectmat.entity.ResourceCache;
import org.enast.hummer.perfectmat.aop.DataConverter;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.enast.hummer.perfectmat.converter.service.IoConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-21 11:49
 * @update 2019-09-21 11:49
 **/
@Component
@DataConverter(keys = "IOList")
public class IoConverterImpl extends AbstractConverter implements IoConverter {

    /**
     * 巡检正常的情况下,必检项,没有检测到设置为-1
     *
     * @param status
     */
    @Override
    public void resetStatus(Map<String, Object> status) {

    }

    @Override
    public void analysis(ResourceCache simpleVO, JsonNode object, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        log.debug("IOListConverter");
        array2JsonStr("ioList", object, status);
    }

    @Override
    public void analysis(List<IOList> list, Map<String, Object> status) {
        array2JsonStr("ioList", list, status);
    }
}
