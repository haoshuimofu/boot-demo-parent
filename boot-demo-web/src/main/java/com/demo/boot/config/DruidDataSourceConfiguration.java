package com.demo.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Druid数据源配置
 *
 * @author wude
 * @version 1.0.0
 * @create 2018-04-26 13:53
 */
//@Configuration
//@EnableAutoConfiguration
public class DruidDataSourceConfiguration {

    @Bean
    @Primary // 在同样的DataSource中，首先使用被标注的DataSource
    @ConfigurationProperties(prefix = "demo.datasource")
    public DataSource initDataSource() {
        return new DruidDataSource();
    }
}