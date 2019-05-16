package com.demo.starter.log;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
//        this.pointcut = new AnnotationMatchingPointcut(RestController.class, Log.class);
        //@Pointcut("execution(* com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))")
        //@Pointcut("within(com.test.spring.aop.pointcutexp..*)")
        //@Pointcut("this(com.test.spring.aop.pointcutexp.Intf)")
        //@Pointcut("target(com.test.spring.aop.pointcutexp.Intf)")
        //@Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
        //@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
        //@Pointcut("args(String)")
        this.pointcut = new AspectJExpressionPointcut();
        ((AspectJExpressionPointcut) this.pointcut).setExpression("execution(* com.demo.boot..*.*(..))");
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