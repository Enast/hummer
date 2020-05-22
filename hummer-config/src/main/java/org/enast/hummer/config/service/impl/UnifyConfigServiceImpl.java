package org.enast.hummer.config.service.impl;

import org.enast.hummer.config.config.UnifyConfigProperties;
import org.enast.hummer.config.vo.PropertyType;
import org.enast.hummer.config.service.UnifyConfigService;
import org.enast.hummer.config.vo.UnifyConfigInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置变更核心处理逻辑
 *
 * @author zhujinming6
 * @create 2019-10-18 14:41
 * @update 2019-10-18 14:41
 **/
@Service
@EnableConfigurationProperties(UnifyConfigProperties.class)
public class UnifyConfigServiceImpl implements UnifyConfigService {

    private static final Logger log = LoggerFactory.getLogger(UnifyConfigServiceImpl.class);

    public static Map<String, UnifyConfigInfo> configInfoMap = new HashMap();

    @Resource
    ApplicationContext context;
    @Resource
    UnifyConfigProperties unifyConfigProperties;

    /**
     * 项目启动时,调用
     */
    @Override
    public void resetAllProperties() {
        log.info("resetAllProperties");
//        Map<String, Object> beans = context.getBeansWithAnnotation(EnableUnifyConfig.class);
//        for (Map.Entry<String, Object> entry : beans.entrySet()) {
//            if (entry == null) {
//                continue;
//            }
//            Object bean = entry.getValue();
//            PropertyType propertyType = null;
//            // 对接配置中心，// TODO
//            Properties configPro = nbConfig.getPropertyPlaceHolderConfigurer().getProperties();
//            instanceName = configPro.getProperty(unifyConfigProperties.getSegmentId() + instance);
//            if (StringUtils.isBlank(instanceName)) {
//                log.info("instanceName is null from instanceList");
//                return;
//            }
//            instanceName = StringUtils.getFirstStr(instanceName, ",");
//            Class<? extends Object> classz = AopUtils.getTargetClass(bean);
//            ConfigurationProperties configAnotation = classz.getAnnotation(ConfigurationProperties.class);
//            // 注解配置
//            if (configAnotation == null) {
//                propertyType = PropertyType.ANNOTATION;
//                Field[] fields = classz.getDeclaredFields();
//                // 查找有@Value 注解的属性
//                for (int i = 0; i < fields.length; i++) {
//                    Field field = fields[i];
//                    Value valueAnotation = field.getAnnotation(Value.class);
//                    // TODO 需要测试下,不行 使用 AnnotationUtils.getAnnotation(field,Value.class);
//                    if (valueAnotation == null) {
//                        continue;
//                    }
//                    // 获取属性名称
//                    String propertyExpress = valueAnotation.value();
//                    if (StringUtils.isBlank(propertyExpress)) {
//                        continue;
//                    }
//                    String propertyName = propertyExpress.replace("${", "").replace("}", "").replace(":", "");
//                    if (StringUtils.isBlank(propertyName)) {
//                        continue;
//                    }
//                    setFiledVale(propertyName, configPro, field, propertyType, bean, entry.getKey(), field.getName());
//                }
//            } else {
//                // 类配置
//                propertyType = PropertyType.CLASSZ;
//                String preFix = configAnotation.prefix();
//                if (StringUtils.isBlank(preFix)) {
//                    continue;
//                }
//                Field[] fields = classz.getDeclaredFields();
//                for (int i = 0; i < fields.length; i++) {
//                    Field field = fields[i];
//                    String propertyName = preFix + field.getName();
//                    setFiledVale(propertyName, configPro, field, propertyType, bean, entry.getKey(), field.getName());
//                }
//            }
//        }
        log.info("configInfoMap size ", "size", configInfoMap.size());
    }


    @Override
    public void updateProperty(String propertyName) {
//        UnifyConfigInfo info = configInfoMap.get(propertyName);
//        if (info == null) {
//            return;
//        }
//        Object bean = context.getBean(info.getBeanName());
//        if (bean == null) {
//            log.info("bean is null", "property", "beanName", propertyName, info.getBeanName());
//            return;
//        }
//        Class<? extends Object> classz = AopUtils.getTargetClass(bean);
//        try {
//            Field field = classz.getDeclaredField(info.getFiledName());
//            Properties configPro = nbConfig.getPropertyPlaceHolderConfigurer().getProperties();
//            if (field == null) {
//                setFiledVale(propertyName, configPro, field, bean);
//            }
//        } catch (NoSuchFieldException e) {
//            log.error("updateProperty", e);
//        }
    }

    private boolean setFiledVale(String propertyName, Properties configPro, Field field, Object bean) {
//        String propertyValue = configPro.getProperty(instanceName + "." + propertyName);
//        if (StringUtils.isBlank(propertyValue)) {
//            return true;
//        }
//        // config.properties有值,则使用配置文件的
//        ReflectionUtils.makeAccessible(field);
//        try {
//            field.set(bean, propertyValue);
//        } catch (IllegalAccessException e) {
//            log.error("setFiledVale", e);
//            return false;
//        }
        return true;
    }

    private void setFiledVale(String propertyName, Properties configPro, Field field, PropertyType propertyType, Object bean, String beanName, String filedName) {
        boolean result = setFiledVale(propertyName, configPro, field, bean);
        if (result) {
            UnifyConfigInfo info = new UnifyConfigInfo();
            info.setBeanName(beanName);
            info.setType(propertyType);
            info.setFiledName(filedName);
            configInfoMap.put(propertyName, info);
        }
    }
}
