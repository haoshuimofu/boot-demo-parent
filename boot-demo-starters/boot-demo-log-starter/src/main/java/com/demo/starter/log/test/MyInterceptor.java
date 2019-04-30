package com.demo.starter.log.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author ddmc
 * @Create 2019-04-29 11:49
 */
public class MyInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Method intercepted!");
        return invocation.proceed();
    }
}