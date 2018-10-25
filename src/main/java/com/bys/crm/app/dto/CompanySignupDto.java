package com.bys.crm.app.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CompanySignupDto {

	@NotBlank(message = "name is required")
	@Size(max = 100, message = "name should not exceed 100 characters")
	private String name;

	private AddressDto address;
	
	private String companyPhone;

	@NotBlank(message = "phone number is required")
	private String phone;

	@NotBlank(message = "taxNumber is required")
	@Size(max = 50, message = "taxNumber should not exceed 50 characters")
	private String taxNumber;

	@NotBlank(message = "presenterName is required")
	@Size(max = 50, message = "presenterName should not exceed 50 characters")
	private String presenterName;

	@Email(message = "email is invalid")
	@NotBlank(message = "email is not allow null/empty")
	@Size(max = 50, message = "email should not exceed 50 characters")
	private String email;

	@NotBlank(message = "password is required")
	private String password;
	
	private UserDeviceDto device;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto addressDto) {
		this.address = addressDto;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getPresenterName() {
		return presenterName;
	}

	public void setPresenterName(String presenterName) {
		this.presenterName = presenterName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public UserDeviceDto getDevice() {
		return device;
	}

	public void setDevice(UserDeviceDto device) {
		this.device = device;
	}
	
}
