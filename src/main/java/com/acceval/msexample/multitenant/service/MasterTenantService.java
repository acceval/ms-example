package com.acceval.msexample.multitenant.service;

import org.springframework.data.repository.query.Param;

import com.acceval.msexample.multitenant.model.MasterTenant;


public interface MasterTenantService {
    
    MasterTenant findByTenantId(@Param("tenantId") String tenantId);
}
