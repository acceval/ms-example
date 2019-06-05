package com.acceval.msexample.multitenant.config;

import java.util.List;
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
import com.acceval.msexample.multitenant.model.MasterTenant;
import com.acceval.msexample.multitenant.repository.MasterTenantRepository;


@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger log = 
    		LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);
    
    private Map<String, DataSource> tenantDataSourceMap = new TreeMap<>();
    
    @Autowired
    private Environment env;
    
    @Autowired
    private MasterTenantRepository masterTenantRepository;

    @Override
    protected DataSource selectAnyDataSource() {
        
        if (tenantDataSourceMap.isEmpty()) {
        	
        	List<MasterTenant> masterTenants = masterTenantRepository.findAll();
            log.info(">>>> selectAnyDataSource() -- Total tenants: " + masterTenants.size());
            
            for (MasterTenant masterTenant : masterTenants) {
            	
            	tenantDataSourceMap.put(masterTenant.getTenantId(), 
            			this.createAndConfigureDataSource(masterTenant));
            }        	
        }
        
        return this.tenantDataSourceMap.values().iterator().next();
    }
    
    private DataSource createAndConfigureDataSource(MasterTenant masterTenant) {
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.classname", "org.postgresql.Driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setSchema(masterTenant.getTenantId());
        log.info("Configured datasource: " + masterTenant.getTenantId());
        
        return dataSource;
    }
    

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        
        tenantIdentifier = this.initializeTenantIfLost(tenantIdentifier);

        if (!this.tenantDataSourceMap.containsKey(tenantIdentifier)) {
            
        	List<MasterTenant> masterTenants = masterTenantRepository.findAll();        	
            log.info(">>>> selectDataSource() -- tenant:" + tenantIdentifier + " Total tenants:" + masterTenants.size());
            
            for (MasterTenant masterTenant : masterTenants) {
            	
            	tenantDataSourceMap.put(masterTenant.getTenantId(), 
            			this.createAndConfigureDataSource(masterTenant));                
            }        	
        }
        
        return this.tenantDataSourceMap.get(tenantIdentifier);
    }
    
    private String initializeTenantIfLost(String tenantIdentifier) {
    	
        if (TenantContext.getCurrentTenant() == null) {

        	CurrentUser currentUser = PrincipalUtil.getCurrentUser();        	
        	if (currentUser != null) {
        		TenantContext.setCurrentTenant(currentUser.getCompanyCode());
        	}
        }

        if (TenantContext.getCurrentTenant() != null 
        		&& !tenantIdentifier.equals(TenantContext.getCurrentTenant())) {
            tenantIdentifier = TenantContext.getCurrentTenant();
        }
        
        return tenantIdentifier;
    }
}