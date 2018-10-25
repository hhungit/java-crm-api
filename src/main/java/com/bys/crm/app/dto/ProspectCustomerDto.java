package com.bys.crm.app.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

public class ProspectCustomerDto {
	private Long id;

	private DateTime createdDate;

	private DateTime updatedDate;

	private String potentialStatus;

	private String title;

	private String firstName;

	private String lastName;

	private String phone;

	private String cellPhone;

	@Email(message = "Email is invalid")
	@Size(max = 50, message = "Email should not exceed 50 characters")
	private String email;

	private String website;

	private String company;

//	private String potentialSource;

	private String address;

	private String rate;

	private String description;

	private String assign;

	@Valid
	private BranchSummaryDto branch;

//	private String business;

	private BigDecimal revenue;

	private String country;

	private String city;

	private String district;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	private String employeeNo;

	private String createdUser;

	@Valid
	private LocationDto location;

	private String objectType;

	private Integer objectId;
	
	private String gender;

	private String lunarBirthday;

	private DateTime dob;
	
	private CustomerResourcesDto customerResource;

	private String campaignNo;

	private Boolean isAssigned;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPotentialStatus() {
		return potentialStatus;
	}

	public void setPotentialStatus(String potentialStatus) {
		this.potentialStatus = potentialStatus;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

//	public String getPotentialSource() {
//		return potentialSource;
//	}
//
//	public void setPotentialSource(String potentialSource) {
//		this.potentialSource = potentialSource;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

//	public String getBusiness() {
//		return business;
//	}
//
//	public void setBusiness(String business) {
//		this.business = business;
//	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLunarBirthday() {
		return lunarBirthday;
	}

	public void setLunarBirthday(String lunarBirthday) {
		this.lunarBirthday = lunarBirthday;
	}

	public DateTime getDob() {
		return dob;
	}

	public void setDob(DateTime dob) {
		this.dob = dob;
	}

	public CustomerResourcesDto getCustomerResource() {
		return customerResource;
	}

	public void setCustomerResource(CustomerResourcesDto customerResource) {
		this.customerResource = customerResource;
	}

	public String getCampaignNo() {
		return campaignNo;
	}

	public void setCampaignNo(String campaignNo) {
		this.campaignNo = campaignNo;
	}

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

}