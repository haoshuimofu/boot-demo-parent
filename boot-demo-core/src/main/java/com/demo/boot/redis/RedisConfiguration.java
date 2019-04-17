package com.demo.boot.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author ddmc
 * @Create 2019-04-17 18:07
 */
@Configuration
public class RedisConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisClusterConfigurationProperties init() {
        return new RedisClusterConfigurationProperties();
    }

    public @Bean
    RedisConnectionFactory connectionFactory() {

        return new JedisConnectionFactory(
                new RedisClusterConfiguration(clusterProperties.getNodes()));
    }

    @Bean
    public JedisCluster getJedisCluster(RedisClusterConfigurationProperties redisClusterConfigurationProperties) {
        String[] serverArray = redisClusterConfigurationProperties.getClusterNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        return new JedisCluster(nodes, redisClusterConfigurationProperties.getCommandTimeout());
    }


}