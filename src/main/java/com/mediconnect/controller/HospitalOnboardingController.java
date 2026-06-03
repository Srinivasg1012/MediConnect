package com.mediconnect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediconnect.dto.HospitalRequest;
import com.mediconnect.service.TenantProvisioningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hospitals")
@Tag(name="Hospital Onboarding",
	description="Tenant registration APIs"
		)
public class HospitalOnboardingController {

	
	 private final TenantProvisioningService service;

	    public HospitalOnboardingController(
	            TenantProvisioningService service) {
	        this.service = service;
	    }
	    
	    
		private static final Logger log=LoggerFactory.getLogger(HospitalOnboardingController.class);

	
	    @PostMapping("/register")
	    @Operation(
	    		summary="Register hospital",
	    		description="creates a new tenant schema"
	    		
	    		)   
	    public ResponseEntity<String> register(
	            @RequestBody @Valid HospitalRequest request) {
	    	
	    	
	    	log.info("Hospital Registration request received : {} ",request.getHospitalId());

	        service.createTenantSchema(
	                request.getHospitalId());
	        
	        
	        log.info("Hospital Registration Completed : {} ",request.getHospitalId());

	        return ResponseEntity.ok(
	                "Tenant Created Successfully"); 
	
	
}
}
