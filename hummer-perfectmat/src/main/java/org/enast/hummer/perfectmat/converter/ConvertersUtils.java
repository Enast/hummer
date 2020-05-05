package org.enast.hummer.perfectmat.converter;

import org.enast.hummer.common.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhujinming6
 * @create 2019-09-21 13:48
 * @update 2019-09-21 13:48
 **/
public class ConvertersUtils {

    private static Logger logger = LoggerFactory.getLogger(ConvertersUtils.class);

    private static String CONVERTER = "Converter";
    private static String SPECIAL_CONVERTER = "SpecialConverter";
    /**
     * 能力报文解析类集合
     */
    public static Map<String, AbstractConverter> converters = new HashMap<>();
    public static Map<String, AbstractConverter> specialConverters = new HashMap<>();

    public static AbstractConverter getCustomConverter(String name) {
        AbstractConverter converter = converters.get(name);
        if (converter != null) {
            return converter;
        }
        try {
            converter = (AbstractConverter) SpringBeanFactory.getObject(name + CONVERTER);
            if (converter != null) {
                put(name, converter);
            }
        } catch (Exception e) {
            logger.debug("get bean fail :{}", name);
            return null;
        }
        return converter;
    }

    public static AbstractConverter getSpecialConverter(String name) {
        AbstractConverter converter = specialConverters.get(name);
        if (converter != null) {
            return converter;
        }
        try {
            converter = (AbstractConverter) SpringBeanFactory.getObject(name + SPECIAL_CONVERTER);
            if (converter != null) {
                putSpecial(name, converter);
            }
        } catch (Exception e) {
            logger.debug("get bean fail :{}", name);
            return null;
        }
        return converter;
    }

    public static void put(String name, AbstractConverter converter) {
        converters.put(name, converter);
    }

    public static void putSpecial(String name, AbstractConverter converter) {
        specialConverters.put(name, converter);
    }
}
