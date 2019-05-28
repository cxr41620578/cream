/**
 * 
 */
package com.cream.core.spring.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author cream
 * @date 2017-11-03
 *
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfigurer {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
