package com.acceval.msexample.multitenant.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan(
		basePackages = { "com.acceval.msexample.model", "com.acceval.msexample.repository" }
		)
@EnableJpaRepositories(
		basePackages = { "com.acceval.msexample.model", "com.acceval.msexample.repository" }, 
		entityManagerFactoryRef = "tenantEntityManagerFactory", 
		transactionManagerRef = "tenantTransactionManager"
		)
public class TenantDatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(TenantDatabaseConfig.class);

    @Autowired
    private Environment env;

    @Bean(name = "tenantJpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    
    @Bean(name = "tenantTransactionManager")
    public JpaTransactionManager transactionManager(
    		@Qualifier("tenantEntityManagerFactory")
            EntityManagerFactory tenantEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManager);
        return transactionManager;
    }
    
    @Bean(name = "datasourceBasedMultitenantConnectionProvider")
    @ConditionalOnBean(name = "masterEntityManagerFactory")
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {

        return new DataSourceBasedMultiTenantConnectionProviderImpl();
    }
    
    @Bean(name = "currentTenantIdentifierResolver")
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    
    @Bean(name = "tenantEntityManagerFactory")
    @ConditionalOnBean(name = "datasourceBasedMultitenantConnectionProvider")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("datasourceBasedMultitenantConnectionProvider") 
            MultiTenantConnectionProvider connectionProvider,
            @Qualifier("currentTenantIdentifierResolver") 
            CurrentTenantIdentifierResolver tenantResolver) {

        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        //All tenant related entities, repositories and service classes must be scanned
        emfBean.setPackagesToScan(
                new String[] { "com.acceval.msexample.model", 
                		"com.acceval.msexample.repository",
                		"com.acceval.msexample.service" });
        emfBean.setJpaVendorAdapter(jpaVendorAdapter());
        emfBean.setPersistenceUnitName("tenantdb-persistence-unit");
        Map<String, Object> properties = new HashMap<>();
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);        
        properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.NON_CONTEXTUAL_LOB_CREATION, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        properties.put(org.hibernate.cfg.Environment.PHYSICAL_NAMING_STRATEGY, 
        		"com.acceval.core.config.CustomSpringPhysicalNamingStrategy");
        
        emfBean.setJpaPropertyMap(properties);
        
        log.info("tenantEntityManagerFactory set up successfully!");
        return emfBean;
    }
}