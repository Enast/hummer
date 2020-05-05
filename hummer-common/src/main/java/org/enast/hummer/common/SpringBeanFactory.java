package org.enast.hummer.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 动态获取bean
 *
 * @author zhujinming6
 * @create 2017-11-06 19:23
 * @update 2017-11-06 19:23
 **/
public class SpringBeanFactory implements ApplicationContextAware {

    private static final Log log = LogFactory.getLog(SpringBeanFactory.class);

    /**
     * APP 上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 容器启动完会自动注入(赋值) applicationContext
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanFactory.applicationContext == null) {
            SpringBeanFactory.applicationContext = applicationContext;
        }
    }

    /**
     * 通过类类型 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    /**
     * 从当前IOC获取bean
     *
     * @param id bean的id
     * @return
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 获取指定注解的bean集合
     *
     * @param annotationType
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
