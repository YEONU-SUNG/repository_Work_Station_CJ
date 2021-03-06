package com.neo.visitor.config;

import javax.sql.DataSource;

import com.neo.visitor.insa.repository.InsaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(annotationClass = SecondaryMapperScan.class, sqlSessionFactoryRef="secondarySqlSessionFactory",
 basePackageClasses = {
    InsaRepository.class
    }
)
public class SecondaryDatasource {
 
    @Bean(name="secondaryDataSource")
    @ConfigurationProperties(prefix="spring.secondary.datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name="secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactoryBean(@Autowired @Qualifier("secondaryDataSource") DataSource secondaryDataSource, ApplicationContext applicationContext)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(secondaryDataSource);
        //factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config-secondary.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:insa_interface/insa-mapper.xml"));
        return factoryBean.getObject();
    }
    
    @Bean(name="secondarySqlSession")
    public SqlSession secondarySqlSession(@Autowired @Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) {
        return new SqlSessionTemplate(secondarySqlSessionFactory);
    }
     
    @Bean(name="secondaryTransactionManager")
    public DataSourceTransactionManager secondaryTransactionManager(@Autowired @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        return new DataSourceTransactionManager(secondaryDataSource);
    }
 
}
