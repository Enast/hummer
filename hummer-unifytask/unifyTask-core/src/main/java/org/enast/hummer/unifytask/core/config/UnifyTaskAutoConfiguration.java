package org.enast.hummer.unifytask.core.config;


import org.enast.hummer.unifytask.core.common.ServerUtils;
import org.enast.hummer.unifytask.core.remotecall.client.ClientUnifyTaskServiceImpl;
import org.enast.hummer.unifytask.core.aop.impl.UnifyTaskAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zhujinming6
 * @create 2020-04-30 12:55
 * @update 2020-04-30 12:55
 **/
public class UnifyTaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ServerUtils serverUtils() {
        return new ServerUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    ClientUnifyTaskServiceImpl clientUnifyTaskService() {
        return new ClientUnifyTaskServiceImpl();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    DistributedJobLockAspect distributedJobLockAspect() {
//        return new DistributedJobLockAspect();
//    }

    @Bean
    @ConditionalOnMissingBean
    UnifyTaskAspect unifyTaskAspect() {
        return new UnifyTaskAspect();
    }

}
