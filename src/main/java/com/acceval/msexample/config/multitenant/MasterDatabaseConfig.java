package com.acceval.msexample.config.multitenant;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = { "com.acceval.msexample.model", "com.acceval.msexample.repository" }, 
		entityManagerFactoryRef = "masterEntityManagerFactory", 
		transactionManagerRef = "masterTransactionManager"
		)
public class MasterDatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(MasterDatabaseConfig.class);

    @Autowired
    private Environment env;
    
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {

        log.info("Setting up masterDataSource");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.classname", "org.postgresql.Driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setSchema("public");
        
        return dataSource;        
    }

    @Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
    	
        LocalContainerEntityManagerFactoryBean em = 
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource());

        em.setPackagesToScan(new String[] { "com.acceval.msexample.model", 
        	"com.acceval.msexample.repository" });
        
        em.setPersistenceUnitName("masterdb-persistence-unit");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);        
        em.setJpaProperties(hibernateProperties());
        log.info("Setup of masterEntityManagerFactory succeeded.");
        return em;
    }
    

    @Bean(name = "masterTransactionManager")
    public JpaTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
       
    	Properties properties = new Properties();
    	
    	properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.NON_CONTEXTUAL_LOB_CREATION, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        
    	return properties;
    }
    
    
}