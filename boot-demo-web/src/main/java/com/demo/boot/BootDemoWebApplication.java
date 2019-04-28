package com.demo.boot;

import com.demo.boot.redis.RedisClusterConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.demo.boot")
@EnableTransactionManagement
public class BootDemoWebApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(BootDemoWebApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BootDemoWebApplication.class, args);
        // spring.profiles.active
        logger.info("Spring.profile.active: {}", StringUtils.join(ctx.getEnvironment().getActiveProfiles(), ", "));
        RedisClusterConfigurationProperties redisClusterConfigurationProperties = ctx.getBean(RedisClusterConfigurationProperties.class);
        System.out.println(redisClusterConfigurationProperties.getClusterNodes());

    }

    /**
     * 打成war包在独立Tomcat下运行, 本来要继承SpringBootServletInitializer并Override其configure方法
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(BootDemoWebApplication.class);
    }

}
