package com.demo.boot;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.demo.boot")
@EnableTransactionManagement // 开启事务管理
@EnableAspectJAutoProxy // 开启AOP
public class BootDemoWebApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(BootDemoWebApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BootDemoWebApplication.class, args);
        // spring.profiles.active
        logger.info("Spring.profile.active: {}", StringUtils.join(ctx.getEnvironment().getActiveProfiles(), ", "));
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
