package com.demo.starter.log;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 日志统计耗时AOP自动设置
 *
 * @Author wude
 * @Create 2019-04-29 10:09
 */
@Configuration
@EnableConfigurationProperties(LogConfigProperties.class)
public class LogPointcutAdvisorAutoConfiguration extends AbstractPointcutAdvisor {

    private Logger logger = LoggerFactory.getLogger(LogPointcutAdvisorAutoConfiguration.class);

    private Pointcut pointcut;

    private Advice advice;

    @Autowired
    private LogConfigProperties logConfigProperties;

    @PostConstruct
    public void init() {
        logger.info("init LogAutoConfiguration start");
        this.pointcut = new AnnotationMatchingPointcut(RestController.class, Log.class);
        this.advice = new LogMethodInterceptor(logConfigProperties.getExcludeArr());
        logger.info("init LogAutoConfiguration end");
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}