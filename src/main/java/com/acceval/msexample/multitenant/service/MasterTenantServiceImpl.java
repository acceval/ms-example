package com.acceval.msexample.multitenant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acceval.msexample.multitenant.model.MasterTenant;
import com.acceval.msexample.multitenant.repository.MasterTenantRepository;

@Service
public class MasterTenantServiceImpl implements MasterTenantService {

    @Autowired
    MasterTenantRepository masterTenantRepository;

    @Override
    public MasterTenant findByTenantId(String tenantId) {
        return masterTenantRepository.findByTenantId(tenantId);
    }

}
