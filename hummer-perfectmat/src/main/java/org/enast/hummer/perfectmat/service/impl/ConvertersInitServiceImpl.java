package org.enast.hummer.perfectmat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.common.SpringBeanFactory;
import org.enast.hummer.perfectmat.converter.AbstractConverter;
import org.enast.hummer.perfectmat.converter.ConvertersUtils;
import org.enast.hummer.perfectmat.aop.DataConverter;
import org.enast.hummer.perfectmat.aop.DataSpecialConverter;
import org.enast.hummer.perfectmat.service.ConvertersInitService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-25 16:15
 * @update 2019-09-25 16:15
 **/
@Service
public class ConvertersInitServiceImpl implements ConvertersInitService {


    @Override
    public void init() {
        // 初始化通用解析器集合
        customConverters();
        // 初始化特殊处理解析器集合
        specialConverters();
    }

    private void specialConverters() {
        Map<String, Object> converters = SpringBeanFactory.getBeansWithAnnotation(DataSpecialConverter.class);
        for (Map.Entry<String, Object> entry : converters.entrySet()) {
            if (entry == null) {
                continue;
            }
            Object bean = entry.getValue();
            AbstractConverter basicConverter = (AbstractConverter) bean;
            if (basicConverter == null) {
                continue;
            }
            DataSpecialConverter dataSpecialConverter = bean.getClass().getAnnotation(DataSpecialConverter.class);
            String keys = dataSpecialConverter.keys();
            if (StringUtils.isBlank(keys)) {
                continue;
            }
            String[] keysArr = keys.split(",");
            for (int i = 0; i < keysArr.length; i++) {
                if (StringUtils.isBlank(keysArr[i])) {
                    continue;
                }
                ConvertersUtils.putSpecial(keysArr[i], basicConverter);
            }
        }
    }

    private void customConverters() {
        Map<String, Object> converters = SpringBeanFactory.getBeansWithAnnotation(DataConverter.class);
        for (Map.Entry<String, Object> entry : converters.entrySet()) {
            if (entry == null) {
                continue;
            }
            Object bean = entry.getValue();
            AbstractConverter basicConverter = (AbstractConverter) bean;
            if (basicConverter == null) {
                continue;
            }
            DataConverter dataConverter = bean.getClass().getAnnotation(DataConverter.class);
            String keys = dataConverter.keys();
            if (StringUtils.isBlank(keys)) {
                continue;
            }
            String[] keysArr = keys.split(",");
            for (int i = 0; i < keysArr.length; i++) {
                if (StringUtils.isBlank(keysArr[i])) {
                    continue;
                }
                ConvertersUtils.put(keysArr[i], basicConverter);
            }
        }
    }
}
