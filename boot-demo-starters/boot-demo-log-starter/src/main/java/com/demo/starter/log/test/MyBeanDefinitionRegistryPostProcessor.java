package com.demo.starter.log.test;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @Author ddmc
 * @Create 2019-04-29 11:44
 */
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String MY_ADVISOR_BEAN_NAME = "com.starter";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Ensure an auto-proxy creator is registered.
        AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);

        RootBeanDefinition beanDefinition = new RootBeanDefinition(MyPointcutAdvisor.class);
        // Bean will only be auto-proxied if it has infrastructure role.
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(MY_ADVISOR_BEAN_NAME, beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory registry) throws BeansException {

    }
}