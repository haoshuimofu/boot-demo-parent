package com.demo.boot;

import com.demo.starter.log.Log;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.demo.boot")
@EnableTransactionManagement // 开启事务管理
@EnableAspectJAutoProxy // 开启AOP
public class BootDemoWebApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(BootDemoWebApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BootDemoWebApplication.class, args);
        // spring.profiles.active
        logger.info("Spring.profile.active: {}", StringUtils.join(ctx.getEnvironment().getActiveProfiles(), ", "));

        RequestMappingHandlerMapping handlerMapping = ctx.getBean(RequestMappingHandlerMapping.class);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            Log log = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Log.class);
            if (log == null) {
                log = handlerMethod.getMethod().getAnnotation(Log.class);
            }
            if (log != null) {
                System.out.println(String.format("%s -> %s", requestMappingInfo.getPatternsCondition().getPatterns().iterator().next(), handlerMethod.getMethod().getName()));
            }
        }
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
