package com.bys.crm.app.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

public class EmployeeDto {
	private Integer id;
	
	@Size(max = 150, message = "name should not exceed 150 characters")
	private String name;
	
	@Size(max = 50, message = "employee number should not exceed 50 characters")
	private String employeeNumber;

	private String firstName;

	private String lastName;

	private DateTime dob;
	
	@Size(max = 100, message = "birth place should not exceed 100 characters")
	private String birthPlace;
	
	@Size(max = 50, message = "employee id number should not exceed 50 characters")
	private String employeeIdNumber;
	
	@Size(max = 50, message = "gender should not exceed 50 characters")
	private String gender;
	
	@Size(max = 50, message = "phone should not exceed 50 characters")
	private String phone;
	
	@Email(message = "email is invalid")
	@Size(max = 50, message = "email should not exceed 50 characters")
	private String email;
	
	@Size(max = 50, message = "tax number should not exceed 50 characters")
	private String taxNumber;
	
	@Size(max = 200, message = "address should not exceed 200 characters")
	private String address;
	
	@Valid
	private BranchSummaryDto branch;

	private String createdUser;

	private DateTime createdDate;

	private String updatedUser;

	private DateTime updatedDate;

	private byte[] avatar;

	@Valid
	private Set<PrivilegeGroupDto> privilegeGroups;

	private Set<EmployeeSummaryDto> groups = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public DateTime getDob() {
		return dob;
	}

	public void setDob(DateTime dob) {
		this.dob = dob;
	}


	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public void setEmployeeIdNumber(String employeeIdNumber) {
		this.employeeIdNumber = employeeIdNumber;
	}

	public String getEmployeeIdNumber() {
		return employeeIdNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Set<PrivilegeGroupDto> getPrivilegeGroups() {
		return privilegeGroups;
	}

	public void setPrivilegeGroups(Set<PrivilegeGroupDto> privilegeGroups) {
		this.privilegeGroups = privilegeGroups;
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

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Set<EmployeeSummaryDto> getGroups() {
		return groups;
	}

	public void setGroups(Set<EmployeeSummaryDto> groups) {
		this.groups = groups;
	}

}
