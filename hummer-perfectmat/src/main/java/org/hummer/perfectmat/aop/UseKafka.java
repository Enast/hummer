package org.hummer.perfectmat.aop;

import java.lang.annotation.*;

/**
 * @author zhujinming6
 * @create 2019-11-29 17:03
 * @update 2019-11-29 17:03
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseKafka {}
