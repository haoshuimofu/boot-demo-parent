package com.ddmc.privilege.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 日志统计耗时AOP自动设置
 *
 * @Author wude
 * @Create 2019-04-29 10:09
 */
@EnableConfigurationProperties(PrivilegeConfigProperties.class)
@ConditionalOnBean(RequestMappingHandlerMapping.class)
public class PrivilegePointcutAdvisorAutoConfiguration {

    @Autowired
    private PrivilegeConfigProperties privilegeConfigProperties;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Bean
    public PrivilegeCollector initPrivilegeCollector() {
        if (privilegeConfigProperties.isEnable()) {
            return new PrivilegeCollector(requestMappingHandlerMapping);
        } else {
            return null;
        }
    }

}