package org.enast.hummer.cluster.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujinming6
 * @create 2019-11-13 10:25
 * @update 2019-11-13 10:25
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributeLock {

    /**
     * consul KV 的key
     *
     * @return
     */
    String key();

    String value() default "";

    /**
     * 锁过期时间
     * -1 不过期,需要手动释放
     *
     * @return
     */
    int ttl() default -1;

    /**
     * 是否堵塞
     *
     * @return
     */
    boolean block() default false;
}
