package com.acceval.msexample.multitenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acceval.msexample.multitenant.model.MasterTenant;


@Repository
public interface MasterTenantRepository extends JpaRepository<MasterTenant, Long> {

	@Query("select p from MasterTenant p where p.tenantId = :tenantId")
    MasterTenant findByTenantId(@Param("tenantId") String tenantId);
}
