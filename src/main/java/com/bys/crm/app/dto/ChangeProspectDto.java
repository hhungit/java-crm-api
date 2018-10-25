package com.bys.crm.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class ChangeProspectDto {
	private Long id;

	@Size(max = 50, message = "customerName should not exceed 100 characters")
	private String customerName;

	@Size(max = 50, message = "business should not exceed 100 characters")
	private String business;

	@Size(max = 50, message = "title should not exceed 100 characters")
	private String title;

	@Size(max = 50, message = "firstName should not exceed 50 characters")
	private String firstName;

	@Size(max = 50, message = "lastName should not exceed 50 characters")
	private String lastName;
	
	@Email(message = "email is invalid")
	@Size(max = 100, message = "email should not exceed 100 characters")
	private String email;

	@Size(max = 50, message = "assignedTo should not exceed 50 characters")
	private String assignedTo;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	private int createCustomer;

	private int createContact;

	private String customerType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public int getCreateCustomer() {
		return createCustomer;
	}

	public void setCreateCustomer(int createCustomer) {
		this.createCustomer = createCustomer;
	}

	public int getCreateContact() {
		return createContact;
	}

	public void setCreateContact(int createContact) {
		this.createContact = createContact;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

}
