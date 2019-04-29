package com.demo.starter.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Log方法拦截器
 *
 * @Author wude
 * @Create 2019-04-29 10:11
 */
public class LogMethodInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogMethodInterceptor.class);

    private List<String> exclude;

    public LogMethodInterceptor(String[] exclude) {
        this.exclude = Arrays.asList(exclude);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        if (exclude.contains(methodName)) {
            return invocation.proceed();
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        logger.info("### 方法执行耗时统计: [{}.{}] 耗时[{}]毫秒!!! ", invocation.getMethod().getDeclaringClass().getName(), methodName, (end - start));
        return result;
    }

}