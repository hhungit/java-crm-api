package com.bys.crm.app.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.bys.crm.domain.erp.model.ADUsers;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ForgotPasswordDto {
	
	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	String email;
	
	@JsonIgnore
	private ADUsers user;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ADUsers getUser() {
		return user;
	}

	public void setUser(ADUsers user) {
		this.user = user;
	}
	
	

}
