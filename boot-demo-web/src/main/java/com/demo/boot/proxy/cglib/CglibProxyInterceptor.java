package com.demo.boot.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-29 13:53
 */
public class CglibProxyInterceptor implements MethodInterceptor {
    private Object target;

    public CglibProxyInterceptor(Object target) {
        this.target = target;
    }

    /**
     * 返回代理对象     * 具体实现，暂时先不追究。
     */
    public Object createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);//回调函数  拦截器
        // 设置代理对象的父类,可以看到代理对象是目标对象的子类。所以这个接口类就可以省略了。
        enhancer.setSuperclass(this.target.getClass());
        return enhancer.create();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("aaaaa");//切面方法a();
        method.invoke(this.target, objects);//调用目标类的目标方法
        System.out.println("bbbbb");//切面方法f();
        return null;
    }

//    @Override
//    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        return null;
//    }
}