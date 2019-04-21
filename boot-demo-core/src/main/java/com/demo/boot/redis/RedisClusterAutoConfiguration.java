package com.demo.boot.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author ddmc
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
                    RedisNode redisNode = new RedisNode(redisNodeInfo[0], Integer.parseInt(redisNodeInfo[1]));
                    return redisNode;
                }).collect(Collectors.toList());
        redisClusterConfiguration.setClusterNodes(redisNodes);
        redisClusterConfiguration.setMaxRedirects(Integer.parseInt(redisClusterConfigurationProperties.getMaxRedirects()));
        return redisClusterConfiguration;
    }

    public @Bean
    RedisConnectionFactory connectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMinIdle(300);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setMaxWaitMillis(6000);
        jedisPoolConfig.setTestOnBorrow(true);

        return new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
    }

    public @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

//    public @Bean
//    RedisConnectionFactory connectionFactory() {
//
//        return new JedisConnectionFactory(
//                new RedisClusterConfiguration(clusterProperties.getNodes()));
//    }

//    @Bean
//    public JedisCluster getJedisCluster(RedisClusterConfigurationProperties redisClusterConfigurationProperties) {
//        String[] serverArray = redisClusterConfigurationProperties.getClusterNodes().split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//        }
//        return new JedisCluster(nodes, redisClusterConfigurationProperties.getCommandTimeout());
//    }


}