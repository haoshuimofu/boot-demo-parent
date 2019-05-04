package com.demo.boot.privilege.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Privilege自动配置
 *
 * @Author wude
 * @Create 2019-04-29 10:09
 */
@EnableConfigurationProperties(PrivilegeProperties.class)
@ConditionalOnBean(RequestMappingHandlerMapping.class)
public class PrivilegeAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(PrivilegeAutoConfiguration.class);


    @Autowired
    private PrivilegeProperties privilegeProperties;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Bean
    @ConditionalOnMissingBean(PrivilegeCollector.class)
    public PrivilegeCollector initPrivilegeCollector() {
        privilegeProperties.setInit(true);
        return new PrivilegeCollector(privilegeProperties, requestMappingHandlerMapping);
    }

}