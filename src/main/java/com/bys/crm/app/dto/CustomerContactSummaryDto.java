package com.bys.crm.app.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class CustomerContactSummaryDto {
	private Long id;

	@Size(max = 50, message = "firstName should not exceed 50 characters")
	private String firstName;

	@Size(max = 50, message = "lastName should not exceed 50 characters")
	private String lastName;

	@Email(message = "email is invalid")
	@Size(max = 100, message = "email should not exceed 100 characters")
	private String email;

	@Size(max = 50, message = "phone should not exceed 50 characters")
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
