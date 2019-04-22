package com.demo.boot.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RedisCluster配置
 *
 * @Author wude
 * @Create 2019-04-17 18:07
 */
@Configuration
public class RedisClusterAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisClusterConfigurationProperties redisClusterConfigurationProperties() {
        return new RedisClusterConfigurationProperties();
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(RedisClusterConfigurationProperties redisClusterConfigurationProperties) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        List<RedisNode> redisNodes = Stream.of(redisClusterConfigurationProperties.getClusterNodes().split(",")).map(hostAndPort -> {
            String[] redisNodeInfo = hostAndPort.split(":");
            return new RedisNode(redisNodeInfo[0], Integer.parseInt(redisNodeInfo[1]));
        }).collect(Collectors.toList());
        redisClusterConfiguration.setClusterNodes(redisNodes);
        redisClusterConfiguration.setMaxRedirects(Integer.parseInt(redisClusterConfigurationProperties.getMaxRedirects()));
        return redisClusterConfiguration;
    }

    public @Bean
    RedisConnectionFactory connectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(10);
        // the maximum number of "active" objects has been reached
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxWaitMillis(6000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(false);

        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    public @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}