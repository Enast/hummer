package org.enast.hummer.perfectmat.aop;

import java.lang.annotation.*;

/**
 * 通用探针报文注解
 *
 * @author zhujinming6
 * @create 2019-09-25 16:00
 * @update 2019-09-25 16:00
 **/
@Target(ElementType.TYPE)// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)//定义注解的作用目标**作用范围字段、枚举的常量/方法
@Documented//说明该注解将被包含在javadoc中
public @interface DataConverter {
    // 使用这个解析器的json报文的keys,多个key逗号隔开
    String keys();
}
