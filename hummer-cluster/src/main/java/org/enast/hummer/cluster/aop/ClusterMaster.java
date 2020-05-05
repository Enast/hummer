package org.enast.hummer.cluster.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujinming6
 * @create 2019-10-12 13:40
 * @update 2019-10-12 13:40
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClusterMaster {

}
