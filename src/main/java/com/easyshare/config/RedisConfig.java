package com.easyshare.config;

import com.alibaba.fastjson2.support.spring6.data.redis.FastJsonRedisSerializer;
import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        /* 指定字符串键的序列化方式 */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /* 指定字符串值的序列化方式 */
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        /* 指定哈希表键的序列化方式 */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        /* 指定哈希表值的序列化方式 */
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));
        return redisTemplate;
    }
}
