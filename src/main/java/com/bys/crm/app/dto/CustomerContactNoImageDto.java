package com.bys.crm.app.dto;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

public class CustomerContactNoImageDto {
	private Long id;

	@Valid
	private CustomerSummaryDto customer;

	private DateTime birthday;

	@Size(max = 50, message = "firstName should not exceed 50 characters")
	private String firstName;

	@Size(max = 50, message = "lastName should not exceed 50 characters")
	private String lastName;

	@Size(max = 50, message = "title should not exceed 50 characters")
	private String title;

	@Email(message = "email is invalid")
	@Size(max = 100, message = "email should not exceed 100 characters")
	private String email;

	@Size(max = 100, message = "website should not exceed 100 characters")
	private String website;

	@Size(max = 50, message = "phone should not exceed 50 characters")
	private String phone;

	@Size(max = 100, message = "type should not exceed 100 characters")
	private String type;

	@Size(max = 510, message = "information should not exceed 510 characters")
	private String information;

	private String address;

	private String assistant;

	private String assistantPhone;

	private String cellularPhone;

	private String homePhone;

	private String jobTitle;

	private String potentialSource;

	private String secondaryPhone;

	@Valid
	private BranchSummaryDto branch;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	@Valid
	private ProspectCustomerSummaryDto prospect;

	private String employeeNo;

	@Valid
	private LocationDto location;

	private String department;

	private String assignedTo;

	private String district;

	private String city;

	private String country;

	private String address2;

	private String district2;

	private String city2;

	private String country2;

	private String createdUser;

	private DateTime createdDate;

	private String updatedUser;

	private DateTime updatedDate;

	private Set<CustomerContactGroupDto> customerContactGroups;

	private Boolean isAssigned;

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getAssistantPhone() {
		return assistantPhone;
	}

	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}

	public String getCellularPhone() {
		return cellularPhone;
	}

	public void setCellularPhone(String cellularPhone) {
		this.cellularPhone = cellularPhone;
	}

//	public String getCompany() {
//		return company;
//	}
//
//	public void setCompany(String company) {
//		this.company = company;
//	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPotentialSource() {
		return potentialSource;
	}

	public void setPotentialSource(String potentialSource) {
		this.potentialSource = potentialSource;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public ProspectCustomerSummaryDto getProspect() {
		return prospect;
	}

	public void setProspect(ProspectCustomerSummaryDto prospect) {
		this.prospect = prospect;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDistrict2() {
		return district2;
	}

	public void setDistrict2(String district2) {
		this.district2 = district2;
	}

	public String getCity2() {
		return city2;
	}

	public void setCity2(String city2) {
		this.city2 = city2;
	}

	public String getCountry2() {
		return country2;
	}

	public void setCountry2(String country2) {
		this.country2 = country2;
	}

	public CustomerSummaryDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSummaryDto customer) {
		this.customer = customer;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public Set<CustomerContactGroupDto> getCustomerContactGroups() {
		return customerContactGroups;
	}

	public void setCustomerContactGroups(Set<CustomerContactGroupDto> customerContactGroups) {
		this.customerContactGroups = customerContactGroups;
	}

}
