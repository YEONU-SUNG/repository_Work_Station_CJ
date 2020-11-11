package com.neo.visitor.config;

import javax.sql.DataSource;
 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
 
@Configuration
public class TertiaryDatasource {
 
    @Bean(name="tertiaryDataSource")
    @ConfigurationProperties(prefix="spring.tertiary.datasource")
    public DataSource tertiaryDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name="tertiarySqlSessionFactory")
    public SqlSessionFactory tertiarySqlSessionFactoryBean(@Autowired @Qualifier("tertiaryDataSource") DataSource tertiaryDataSource, ApplicationContext applicationContext)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(tertiaryDataSource);
        //factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config-secondary.xml"));
        //factoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper-tertiary/**/*.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:sms_interface/sms-mapper.xml"));
        return factoryBean.getObject();
    }
    
    @Bean(name="tertiarySqlSession")
    public SqlSession tertiarySqlSession(@Autowired @Qualifier("tertiarySqlSessionFactory") SqlSessionFactory tertiarySqlSessionFactory) {
        return new SqlSessionTemplate(tertiarySqlSessionFactory);
    }
     
    @Bean(name="tertiaryTransactionManager")
    public DataSourceTransactionManager tertiaryTransactionManager(@Autowired @Qualifier("tertiaryDataSource") DataSource tertiaryDataSource) {
        return new DataSourceTransactionManager(tertiaryDataSource);
    }
 
}
