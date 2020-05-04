package org.hummer.perfectmat.converter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.hummer.perfectmat.ResourceCache;
import org.hummer.perfectmat.aop.DataConverter;
import org.hummer.perfectmat.converter.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-23 14:32
 * @update 2019-09-23 14:32
 **/
@Component
@DataConverter(keys = "processeList")
public class ProcessesConverterImpl extends AbstractConverter {

    /**
     * 巡检正常的情况下,必检项,没有检测到设置为-1
     *
     * @param status
     */
    @Override
    public void resetStatus(Map<String, Object> status) {

    }

    /**
     * 服务器进程列表存json
     *
     * @param simpleVO
     * @param object
     * @param status
     */
    @Override
    public void analysis(ResourceCache simpleVO, JsonNode object, Map<String, Object> status, JsonNode jObject, String businessType, String code) {
        log.debug("processeListConverter");
        array2JsonStr("processes", object, status);
    }
}
