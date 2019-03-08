package com.crm.graduation.crmsystem.config.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Bean
    public RedisTemplate redisTemplateFactory(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(jedisPoolConfig());
    }

    /**
     * jedis 连接池
     * @return
     */
    @Bean(name="jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxTotal(8);
        return jedisPoolConfig;
    }

    /**
     * 生成 RetryTemplate 用于代替xml 里的<bean></bean> 属性
     * @return
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(50);
        retryTemplate.setRetryPolicy(policy);
        return retryTemplate;
    }
}
