package org.hummer.task.client.config;

import org.hummer.task.client.init.TaskClientInitService;
import org.hummer.task.client.service.impl.TaskExecuteServiceImpl;
import org.hummer.task.client.service.impl.TestServiceImpl;
import org.hummer.task.client.web.TaskDispatchRestful;
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

    @Bean
    @ConditionalOnMissingBean
    TestServiceImpl testService() {
        return new TestServiceImpl();
    }

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
