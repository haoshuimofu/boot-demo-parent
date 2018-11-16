package com.demo.boot.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-29 13:45
 */
public class JdkProxyTest {
    public static void main(String[] args) {

        //目标对象
        TargetObject target = new TargetObject();
        //拦截器
        JdkProxyInterceptor myInterceptor = new JdkProxyInterceptor(target);

        /*
         *  Proxy.newProxyInstance参数：
         * 	1、目标类的类加载器
         * 	2、目标类的所有的接口
         *  3、拦截器
         */
        //代理对象，调用系统方法自动生成
        TargetInterface proxyObj = (TargetInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), myInterceptor);
        proxyObj.doSomething();

    }
}