package org.hummer.task.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujinming6
 * @create 2019-10-11 11:14
 * @update 2019-10-11 11:14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnifyTask {
    /**
     * 中文名称
     *
     * @return
     */
    String name() default "";

    /**
     * 任务标识,组件内部保持唯一
     *
     * @return
     */
    String taskNo();

    /**
     * 执行的cron
     *
     * @return
     */
    String cron();
}
