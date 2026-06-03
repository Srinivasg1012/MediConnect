package com.mediconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediconnect.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant,Long> {

	
	boolean existsByHospitalId(String hospitalId);
	
}
