package org.enast.hummer.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义RedisTemplate，使用FastJson序列化器代替默认的Jdk序列化器。
     *
     * @param redisConnectionFactory
     * @return 自定义RedisTemplate
     */
    @Primary
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new FastJson2JsonRedisSerializer("hummer"));
        redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer("hummer"));
        redisTemplate.setHashKeySerializer(new FastJson2JsonRedisSerializer("hummer"));
        redisTemplate.setHashValueSerializer(new FastJson2JsonRedisSerializer("hummer"));
        redisTemplate.setDefaultSerializer(new FastJson2JsonRedisSerializer("hummer"));
        return redisTemplate;
    }
}
