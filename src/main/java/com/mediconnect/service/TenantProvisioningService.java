package com.mediconnect.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediconnect.entity.Tenant;
import com.mediconnect.exception.TenantProvisioningException;
import com.mediconnect.repository.TenantRepository;

@Service
public class TenantProvisioningService {
	
	
	
	private final JdbcTemplate jdbcTemplate;
	
	private final TenantRepository tenantRepository;
	
	
	public TenantProvisioningService(JdbcTemplate jdbcTemplate,TenantRepository tenantRepository) {
		
		this.jdbcTemplate=jdbcTemplate;
		
		this.tenantRepository=tenantRepository;
		
	}
	
	private static final Logger log=LoggerFactory.getLogger(TenantProvisioningService.class);

	
	
	@Transactional
	public void createTenantSchema(String tenantId) {
		
		
		if(tenantRepository.existsByHospitalId(tenantId)) {
			throw new TenantProvisioningException("Tenant already exists");
		}
		
		String schemaName="tenant_"+tenantId;
		
		String sql="CREATE DATABASE IF NOT EXISTS "+ schemaName;
		
		log.info("executing sql : {} ",sql);
		
		
		jdbcTemplate.execute(sql);
		
		
		log.info("created schema : {}" ,schemaName);
		
		
	    createTables(schemaName);

		
	    Tenant tenant = new Tenant();

	    tenant.setHospitalId(tenantId);
	    tenant.setDatabaseName(schemaName);
	    tenant.setStatus("ACTIVE");
	    tenant.setCreatedAt(
	            LocalDateTime.now());
	    
	    //Rollback
//	    
//	    if(true){
//	    	   throw new RuntimeException(
//	    	      "Testing rollback"
//	    	   );
//	    	}

	    tenantRepository.save(tenant);

	    log.info(
	            "Tenant metadata saved for {}",
	            tenantId
	    );
	    
		
	}
	
	public void createTables(String schemaName) {


			
		String doctorsql=""" 
				CREATE TABLE IF NOT EXISTS %s.doctors(
				id BIGINT PRIMARY KEY AUTO_INCREMENT,
				name VARCHAR(100) NOT NULL,
				specialization VARCHAR(100),
				experience INT
				)
				""".formatted(schemaName);
	

		jdbcTemplate.execute(doctorsql);
		
		
		log.info("created doctors table in {} ",schemaName);
		
	
	

	
	String patientsSql="""
			CREATE TABLE IF NOT EXISTS %s.patients(
			id BIGINT PRIMARY KEY AUTO_INCREMENT,
			name VARCHAR(100),
			age INT,
			mobile VARCHAR(15)	
			)	
			""".formatted(schemaName);
	
	jdbcTemplate.execute(patientsSql);
	
	log.info("created patients table in {} ",schemaName);
	
	
	
	
		String appointmentsSql="""
				CREATE TABLE IF NOT EXISTS %s.appointments(
				id BIGINT PRIMARY KEY AUTO_INCREMENT,
				patient_name VARCHAR(100),
				doctor_name VARCHAR(100),
				appointment_date DATE	
				)
				""".formatted(schemaName);
	
	
	jdbcTemplate.execute(appointmentsSql);
	
	log.info("created appointments table in {} ",schemaName);
	

}
}