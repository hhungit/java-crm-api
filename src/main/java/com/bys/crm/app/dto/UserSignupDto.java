package com.bys.crm.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.GELocations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class UserSignupDto {
	
	@NotBlank(message = "name is required")
	@Size(max = 100, message = "name should not exceed 100 characters")
	private String name;
	
	@NotBlank(message = "phone number is required")
	private String phone;
	
	@Email(message = "invalid email format")
	@NotBlank(message = "email is required")
	private String email;
	
	@NotNull(message = "birthday is required")
	private long dob;
	
	@Valid
	private AddressDto address;
	
	@NotNull (message = "gender is required")
	private Gender gender;
	
	@NotBlank(message = "password is required")
	private String password;
	
	@JsonIgnore
	private GELocations location;
	
	private UserDeviceDto device;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public long getDob() {
		return dob;
	}
	public void setDob(long dob) {
		this.dob = dob;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public GELocations getLocation() {
		return location;
	}
	public void setLocation(GELocations location) {
		this.location = location;
	}
	public UserDeviceDto getDevice() {
		return device;
	}
	public void setDevice(UserDeviceDto device) {
		this.device = device;
	}
	
}
