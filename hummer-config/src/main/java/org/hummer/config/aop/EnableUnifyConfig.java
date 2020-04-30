package org.hummer.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用统一配置注解
 *
 * @author zhujinming6
 * @create 2019-10-18 14:34
 * @update 2019-10-18 14:34
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableUnifyConfig {

}
