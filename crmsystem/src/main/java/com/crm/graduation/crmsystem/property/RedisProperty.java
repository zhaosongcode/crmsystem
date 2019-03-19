package com.crm.graduation.crmsystem.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix="spring.redis")
@PropertySource(value="classpath:redis.properties")
public class RedisProperty {

    private String host;
    private String password;
    private int port;
}
