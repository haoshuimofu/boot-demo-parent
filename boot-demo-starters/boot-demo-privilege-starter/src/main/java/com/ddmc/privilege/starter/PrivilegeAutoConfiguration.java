package com.ddmc.privilege.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Privilege自动配置
 *
 * @Author wude
 * @Create 2019-04-29 10:09
 */
@Configuration
@EnableConfigurationProperties(PrivilegeProperties.class)
public class PrivilegeAutoConfiguration {

    @Autowired
    private PrivilegeProperties privilegeProperties;

//    @Bean
//    @ConfigurationProperties(prefix = "ddmc.privilege")
//    public PrivilegeProperties privilegeProperties() {
//        return new PrivilegeProperties();
//
//    }

    @Bean
    @ConditionalOnMissingBean(PrivilegeCollector.class)
    public PrivilegeCollector initPrivilegeCollector(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        privilegeProperties.setInit(true);
        return new PrivilegeCollector(privilegeProperties, requestMappingHandlerMapping);
    }

}