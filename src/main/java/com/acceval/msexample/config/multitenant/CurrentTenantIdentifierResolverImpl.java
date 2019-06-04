package com.acceval.msexample.config.multitenant;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.acceval.msexample.config.TenantContext;

public class CurrentTenantIdentifierResolverImpl
        implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT_ID = "smartco";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        return StringUtils.isNotBlank(tenantId) ? tenantId : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
