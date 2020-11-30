package com.neo.visitor.config;

import javax.sql.DataSource;

import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingRepository;
import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingSiteMappingRepository;
import com.neo.visitor.domain.buildingSiteMapping.repository.SiteRepository;
import com.neo.visitor.domain.education.repository.ContentsRepository;
import com.neo.visitor.domain.faq.repository.FaqRepository;
import com.neo.visitor.domain.file.repository.FileRepository;
import com.neo.visitor.domain.notice.repository.NoticeRepository;
import com.neo.visitor.domain.personalinfo.repository.PersonalinfoRepository;
import com.neo.visitor.domain.user.repository.AdminUserRepository;
import com.neo.visitor.domain.user.repository.HostRepository;
import com.neo.visitor.domain.visitor.repository.VisitorDashboardRepository;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;
import com.neo.visitor.domain.visitor.repository.VisitorInoutTimeRepository;
import com.neo.visitor.domain.visitor.repository.VisitorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(annotationClass = PrimaryMapperScan.class, sqlSessionFactoryRef = "sqlSessionFactory", basePackageClasses = {
    ContentsRepository.class
    ,FaqRepository.class
    , FileRepository.class
    , NoticeRepository.class
    , PersonalinfoRepository.class
    , AdminUserRepository.class
    , HostRepository.class
    , VisitorDashboardRepository.class
    , VisitorHistoryRepository.class
    , VisitorInoutTimeRepository.class
    , VisitorRepository.class
    , BuildingRepository.class
    , BuildingSiteMappingRepository.class
    , SiteRepository.class
} )
public class PrimaryDatasource {
    
    @Primary
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.primary.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Primary
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Autowired @Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.neo.visitor.domain");
        //factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config-primary.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/*.xml"));
        
        return factoryBean.getObject();
    }
 
    @Primary
    @Bean(name="sqlSession")
    public SqlSessionTemplate sqlSession(@Autowired @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
 
    @Primary
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(@Autowired @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}