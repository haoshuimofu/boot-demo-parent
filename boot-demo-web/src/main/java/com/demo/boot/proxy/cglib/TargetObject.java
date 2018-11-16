package com.demo.boot.proxy.cglib;

import com.demo.boot.proxy.jdk.TargetInterface;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-29 13:40
 */
public class TargetObject implements TargetInterface {
    @Override
    public void doSomething() {
        System.out.println(this.getClass().getCanonicalName() + ".doSomething is running...");
    }
}