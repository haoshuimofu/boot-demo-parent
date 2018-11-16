package com.demo.boot.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-29 13:42
 */
public class JdkProxyInterceptor implements InvocationHandler {

    private Object target;

    public JdkProxyInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("Before  Method[%s]...", method.getName()));
        method.invoke(this.target, args);//调用目标类的目标方法
        System.out.println(String.format("After  Method[%s]...", method.getName()));
        return null;
    }
}