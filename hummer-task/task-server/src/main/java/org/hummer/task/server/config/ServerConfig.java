package org.hummer.task.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.smallframework.spring.DaoTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import sf.database.OrmConfig;
import sf.spring.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class ServerConfig {

    @Bean("taskRest")
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariDataSource dataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
