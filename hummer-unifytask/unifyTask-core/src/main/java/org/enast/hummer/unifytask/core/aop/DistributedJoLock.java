package org.enast.hummer.unifytask.core.aop;

import java.lang.annotation.*;

/**
 * @author zhujinming6
 * @create 2019-08-26 16:02
 * @update 2019-08-26 16:02
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedJoLock {

    /**
     * 锁的key前缀
     */
    String lockKeyPre() default "PRE";

    /**
     * 锁的key,唯一
     */
    String lockKey() default "";
}
