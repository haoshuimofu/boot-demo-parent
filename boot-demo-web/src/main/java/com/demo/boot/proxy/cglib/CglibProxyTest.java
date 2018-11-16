package com.demo.boot.proxy.cglib;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-29 13:58
 */
public class CglibProxyTest {
    public static void main(String[] args) {

        //目标对象
        TargetObject target = new TargetObject();
        //拦截器
        CglibProxyInterceptor myInterceptor = new CglibProxyInterceptor(target);
        //代理对象，调用cglib系统方法自动生成
        //注意：代理类是目标类的子类。
        TargetObject proxyObj = (TargetObject) myInterceptor.createProxy();
        proxyObj.doSomething();

    }
}