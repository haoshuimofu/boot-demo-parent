package com.demo.boot.dao.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-30 13:05
 */
@Configuration
@MapperScan(basePackages = {"com.demo.boot.order.dao"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderDaoConfiguration {


    @Bean(name = "orderDataSource")
    //@Primary //必须加此注解，不然报错，下一个类则不需要添加
    @ConfigurationProperties(prefix = "order.datasource") // prefix值必须是application.properteis中对应属性的前缀
    public DataSource userDataSource() {
//        return DataSourceBuilder.create().build();
        return new DruidDataSource();
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:com/demo/boot/order/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate businessSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("orderTransactionManager")
//    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("orderDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}