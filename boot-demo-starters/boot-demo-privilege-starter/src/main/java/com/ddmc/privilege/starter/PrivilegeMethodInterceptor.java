package com.ddmc.privilege.starter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log方法拦截器
 *
 * @Author wude
 * @Create 2019-04-29 10:11
 */
public class PrivilegeMethodInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(PrivilegeMethodInterceptor.class);


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        logger.info("### 方法执行耗时统计: [{}.{}] 耗时[{}]毫秒!!! ", invocation.getMethod().getDeclaringClass().getName(), methodName, (end - start));
        return result;
    }

}