package com.mediconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class HospitalRequest {
	
	
	
	@NotBlank(message="Hospital ID cannot be blank")
	
	@Size(
			min=3,
			max=50,
			message="Hospital ID must be between 3 and 50 characters "
			)
	@Pattern(
			regexp="^[a-zA-Z0-9_-]+$",
			message="Hospital ID can contain only letters, numbers, -, and _ "
			)
	
	
	private String hospitalId;

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	
	
	
	
	
	

}
