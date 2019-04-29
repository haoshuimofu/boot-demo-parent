package com.demo.starter.log.test;

import com.demo.starter.log.Log;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * @Author ddmc
 * @Create 2019-04-29 11:47
 */
public class MyPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
                        return matches(method, targetClass);
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return (method.getAnnotation(Log.class) != null) ? true : false;
                    }

                    @Override
                    public boolean isRuntime() {
                        return true;
                    }
                };
            }

            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return (clazz.getAnnotation(Controller.class) != null) ? true : false;
                    }
                };
            }
        };
    }
}