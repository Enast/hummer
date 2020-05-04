package org.hummer.task.config;

import org.hummer.task.ServerUtils;
import org.hummer.task.client.init.TaskClientInitService;
import org.hummer.task.client.remotecall.client.ClientUnifyTaskServiceImpl;
import org.hummer.task.client.service.impl.TaskExecuteServiceImpl;
import org.hummer.task.client.service.impl.TestServiceImpl;
import org.hummer.task.client.web.TaskDispatchRestful;
import org.hummer.task.server.biz.impl.UnifyTaskBizImpl;
import org.hummer.task.server.biz.impl.UnifyTaskLogBizImpl;
import org.hummer.task.server.remotecall.client.ServerUnifyTaskServiceImpl;
import org.hummer.task.server.service.impl.UnifyTaskDispatchServiceImpl;
import org.hummer.task.server.service.impl.UnifyTaskServiceImpl;
import org.smallframework.spring.DaoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import sf.database.OrmConfig;
import sf.database.dao.DBClient;
import sf.spring.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author zhujinming6
 * @create 2020-04-30 12:55
 * @update 2020-04-30 12:55
 **/
public class UnifyTaskAutoConfiguration {

    @Bean("taskRest")
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    TaskDispatchRestful taskDispatchRestful(){
        return new TaskDispatchRestful();
    }

    @Bean
    @ConditionalOnMissingBean
    TestServiceImpl testService(){
        return new TestServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    TaskExecuteServiceImpl taskExecuteService(){
        return new TaskExecuteServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    ClientUnifyTaskServiceImpl clientUnifyTaskService(){
        return new ClientUnifyTaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    TaskClientInitService taskClientInitService(){
        return new TaskClientInitService();
    }

    @Bean
    @ConditionalOnMissingBean
    UnifyTaskLogBizImpl unifyTaskLogBiz(@Autowired DBClient dbClient){
        return new UnifyTaskLogBizImpl(dbClient);
    }

    @Bean
    @ConditionalOnMissingBean
    UnifyTaskBizImpl unifyTaskBiz(@Autowired DBClient dbClient){
        return new UnifyTaskBizImpl(dbClient);
    }

    @Bean
    @ConditionalOnMissingBean
    ServerUnifyTaskServiceImpl serverUnifyTaskService() {
        return new ServerUnifyTaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    UnifyTaskDispatchServiceImpl unifyTaskDispatchService() {
        return new UnifyTaskDispatchServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    UnifyTaskServiceImpl unifyTaskService() {
        return new UnifyTaskServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    ServerUtils serverUtils() {
        return new ServerUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public OrmConfig getOrmConfig(DataSource dataSource) {
        DaoTemplate dt = new DaoTemplate(dataSource);
        OrmConfig config = new OrmConfig();
        config.setDbClient(dt);
        config.setPackagesToScan(StringUtils.split("org.hummer",","));
        config.setDbClient(dt);
        config.setUseTail(true);
        config.init();
        return config;
    }

    @Bean(name="daoTemplate")
    @ConditionalOnMissingBean
    public DaoTemplate geDaoTemplate(OrmConfig config) {
        return (DaoTemplate) config.getDbClient();
    }
}
