package com.acceval.msexample.config.multitenant;

import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.acceval.core.security.CurrentUser;
import com.acceval.core.security.PrincipalUtil;
import com.acceval.msexample.config.TenantContext;


@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger log = 
    		LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);
    
    private Map<String, DataSource> tenantDataSourceMap = new TreeMap<>();
    
    @Autowired
    private Environment env;

    @Override
    protected DataSource selectAnyDataSource() {
        
        if (tenantDataSourceMap.isEmpty()) {
        	
        	tenantDataSourceMap.put("smartco", this.createAndConfigureDataSource("smartco"));
        }
        
        return this.tenantDataSourceMap.values().iterator().next();
    }
    
    private DataSource createAndConfigureDataSource(String tenantId) {
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.classname", "org.postgresql.Driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setSchema(tenantId);
        log.info("Configured datasource:" + tenantId);
        
        return dataSource;
    }
    

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        
        tenantIdentifier = this.initializeTenantIfLost(tenantIdentifier);

        if (!this.tenantDataSourceMap.containsKey(tenantIdentifier)) {
            
        	tenantDataSourceMap.put("smartco", this.createAndConfigureDataSource("smartco"));
        }
        return this.tenantDataSourceMap.get(tenantIdentifier);
    }
    
    private String initializeTenantIfLost(String tenantIdentifier) {
    	
        if (TenantContext.getCurrentTenant() == null) {

//        	CurrentUser currentUser = PrincipalUtil.getCurrentUser();
            TenantContext.setCurrentTenant("smartco");
        }

        if (!tenantIdentifier.equals(TenantContext.getCurrentTenant())) {
            tenantIdentifier = TenantContext.getCurrentTenant();
        }
        
        return tenantIdentifier;
    }
}