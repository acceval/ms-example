package com.acceval.msexample.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
public class HibernateConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
     public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, 
    		MultiTenantConnectionProvider multiTenantConnectionProviderImpl, 
    		CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
    	
        Map<String, Object> properties = new HashMap<>();
        
//        properties.putAll(jpaProperties.getHibernateProperties(dataSource));        
        properties.putAll(jpaProperties.getHibernateProperties(new HibernateSettings()));
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.acceval");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        return em;
    }
}
