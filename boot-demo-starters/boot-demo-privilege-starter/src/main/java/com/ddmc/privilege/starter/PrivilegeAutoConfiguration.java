package com.ddmc.privilege.starter;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

    @Autowired
    private PrivilegeProperties privilegeProperties;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired(required = false)
    private PrivilegeController privilegeController;

    @Bean
    public PrivilegeCollector initPrivilegeCollector() {
        if (privilegeController == null) {
            return new PrivilegeCollector(privilegeProperties, requestMappingHandlerMapping);
        }
        return null;
    }

}