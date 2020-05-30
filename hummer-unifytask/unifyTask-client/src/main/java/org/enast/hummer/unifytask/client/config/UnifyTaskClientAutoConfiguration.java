package org.enast.hummer.unifytask.client.config;

import org.enast.hummer.unifytask.client.web.TaskDispatchRestful;
import org.enast.hummer.unifytask.client.init.TaskClientInitService;
import org.enast.hummer.unifytask.client.service.impl.TaskExecuteServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhujinming6
 * @create 2020-04-30 12:55
 * @update 2020-04-30 12:55
 **/
public class UnifyTaskClientAutoConfiguration {

    @Bean("taskRest")
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    TaskDispatchRestful taskDispatchRestful() {
        return new TaskDispatchRestful();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    TestServiceImpl testService() {
//        return new TestServiceImpl();
//    }

    @Bean
    @ConditionalOnMissingBean
    TaskExecuteServiceImpl taskExecuteService() {
        return new TaskExecuteServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    TaskClientInitService taskClientInitService() {
        return new TaskClientInitService();
    }

}
