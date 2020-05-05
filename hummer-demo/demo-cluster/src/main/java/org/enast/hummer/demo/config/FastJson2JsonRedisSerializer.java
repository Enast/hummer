package org.enast.hummer.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * FastJson序列化Redis Cache
 */
public class FastJson2JsonRedisSerializer implements RedisSerializer<Object> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String separator = ".";

    private String keyPrefix;

    public FastJson2JsonRedisSerializer(String keyPrefix) {
        // 添加autotype白名单
        this.keyPrefix = keyPrefix + separator;
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig pc = ParserConfig.getGlobalInstance();
        Field f = ReflectionUtils.findField(ParserConfig.class, "denyList");
        if (f != null) {
            f.setAccessible(true);
            try {
                f.set(pc, new String[0]);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null) {
            return new byte[0];
        }
        String key = JSON.toJSONString(source, SerializerFeature.WriteClassName);
        if(StringUtils.isBlank(key) || key.startsWith(keyPrefix)) {
            return key.getBytes(DEFAULT_CHARSET);
        } else {
            key = keyPrefix + key;
            return key.getBytes(DEFAULT_CHARSET);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String key = new String(bytes, DEFAULT_CHARSET);
        if(StringUtils.isBlank(key) || StringUtils.isBlank(keyPrefix) || !key.startsWith(keyPrefix)) {
            return key;
        }
        int indexOf = key.indexOf(keyPrefix);
        String t = key.substring(indexOf + keyPrefix.length());
        return JSON.parseObject(t, Object.class);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
