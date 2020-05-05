package org.enast.hummer.demo.config.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2019-01-11 12:27
 * @update 2019-01-11 12:27
 **/
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(name = "hummer.redisson.use", havingValue = "true", matchIfMissing = false)
public class RedissonConfig {

    @Resource
    RedisProperties redisProperties;

    @Value("${hummer.redisson.connection.minimumIdle:5}")
    private Integer connectionMinimumIdleSize;

    @Value("${hummer.redisson.connection.poolSize:10}")
    private Integer connectionPoolSize;

    @Value("${hummer.redisson.connection.slave.minimumIdle:10}")
    private Integer slaveConnectionMinimumIdleSize;

    @Value("${hummer.redisson.connection.master.minimumIdle:10}")
    private Integer masterConnectionMinimumIdleSize;

    @Value("${hummer.redisson.connection.master.poolSize:10}")
    private Integer masterConnectionPoolSize;

    @Value("${hummer.redisson.connection.slave.poolSize:10}")
    private Integer slaveConnectionPoolSize;

    @Value("${hummer.redisson.subscription.connection.minimumIdle:1}")
    private Integer subscriptionConnectionMinimumIdleSize;

    @Value("${hummer.redisson.subscription.connection.poolSize:20}")
    private Integer subscriptionConnectionPoolSize;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedission() {
        Config config = new Config();
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (sentinel != null && !CollectionUtils.isEmpty(sentinel.getNodes())) {
            String[] strings = new String[sentinel.getNodes().size()];
            for (int i = 0; i < sentinel.getNodes().size(); i++) {
                strings[i] = "redis://" + sentinel.getNodes().get(i);
            }
            config.useSentinelServers()
                    .setPassword(redisProperties.getPassword())
                    .setMasterName(sentinel.getMaster())
                    .addSentinelAddress(strings)
                    .setReadMode(ReadMode.MASTER_SLAVE)
                    .setDnsMonitoringInterval(5000)
                    .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                    .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                    .setTimeout(3000)
                    .setConnectTimeout(10000)
                    .setIdleConnectionTimeout(10000);
        } else if (cluster != null && !CollectionUtils.isEmpty(cluster.getNodes())) {
            String[] strings = new String[cluster.getNodes().size()];
            for (int i = 0; i < cluster.getNodes().size(); i++) {
                strings[i] = "redis://" + cluster.getNodes().get(i);
            }
            config.useMasterSlaveServers().setMasterAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setPassword(redisProperties.getPassword())
                    .addSlaveAddress(strings)
                    .setReadMode(ReadMode.MASTER_SLAVE)
                    .setSlaveConnectionMinimumIdleSize(slaveConnectionMinimumIdleSize)
                    .setMasterConnectionMinimumIdleSize(masterConnectionMinimumIdleSize)
                    .setMasterConnectionPoolSize(masterConnectionPoolSize)
                    .setSlaveConnectionPoolSize(slaveConnectionPoolSize)
                    .setDnsMonitoringInterval(5000)
                    .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                    .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                    .setTimeout(3000)
                    .setConnectTimeout(10000)
                    .setIdleConnectionTimeout(10000);
        } else {
            config.useSingleServer()
                    .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setPassword(redisProperties.getPassword())
                    .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                    .setConnectionPoolSize(connectionPoolSize)
                    .setDnsMonitoringInterval(5000)
                    .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                    .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                    .setTimeout(3000)
                    .setConnectTimeout(10000)
                    .setIdleConnectionTimeout(10000);
        }
        return Redisson.create(config);
    }

//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
//        return new RedissonConnectionFactory(redisson);
//    }

}
