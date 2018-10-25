package com.bys.crm.app.dto;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.joda.time.DateTime;

public class ObjectAuditDto {
	private String status;

	private DateTime updatedDate;

	private String updatedUser;

	private String potentialStatus;

	private String name;

	private String firstName;

	private String lastName;

	private String phone;

	private String cellPhone;

	private String email;

	private String website;

	private String company;

	private String potentialSource;

	private String address;

	private String rate;

	private String description;

	private String assign;

	private String business;

	private BigDecimal revenue;

	private String country;

	private String city;

	private String district;

	private DateTime birthday;

	private String title;

	private String type;

	private String information;

	private String assistant;

	private String assistantPhone;

	private String cellularPhone;

	private String homePhone;

//	private byte[] image;

	private String jobTitle;

	private String secondaryPhone;

	private String department;

	private String assignedTo;

	private String campaignStatus;

	private String campaignObject;

	private DateTime startDate;

	private DateTime completionDate;

	private String donor;

	private String goals;

	private Long expectedNumber;

	private BigDecimal budget;

	private BigDecimal costs;

	private BigDecimal expectedRevenue;

	private BigDecimal actualRevenue;

	private BigDecimal expectedResults;

	private BigDecimal actualResults;

	private String classify;

	private String potentialSources;

	private BigDecimal amount;

	private String step;

	private BigDecimal probability;

	private String strategy;

	private BigDecimal expectedValue;

	private String customerName;

	private String contactName;

	private DateTime endDate;

	private String activityStatus;

	private String eventType;

	private String activityType;

//	private Long activityObjectTypeId;

	private String activityObjectTypeName;

	private String activityObjectType;

	private String customerNumber;

	private String gender;

	private DateTime dob;

	private String presenterName;

	private String companyPhone;

	private String taxNumber;

	private String bankAcc1;

	private String address3;

	private Long bonusScore;

	private String email2;

	private String fax;

	private Long evaluate;

	private BigDecimal revenueDueYear;

	private String stockCode;

	private String tel1;

	private String tel2;

	private String group;

	private Long rev;

	private Long revType;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	@Valid
	private CustomerSummaryDto customer;

	@Valid
	private CustomerContactSummaryDto customerContact;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getPotentialStatus() {
		return potentialStatus;
	}

	public void setPotentialStatus(String potentialStatus) {
		this.potentialStatus = potentialStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPotentialSource() {
		return potentialSource;
	}

	public void setPotentialSource(String potentialSource) {
		this.potentialSource = potentialSource;
	}

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

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

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

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

//	public byte[] getImage() {
//		return image;
//	}
//
//	public void setImage(byte[] image) {
//		this.image = image;
//	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
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

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public String getCampaignObject() {
		return campaignObject;
	}

	public void setCampaignObject(String campaignObject) {
		this.campaignObject = campaignObject;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(DateTime completionDate) {
		this.completionDate = completionDate;
	}

	public String getDonor() {
		return donor;
	}

	public void setDonor(String donor) {
		this.donor = donor;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public Long getExpectedNumber() {
		return expectedNumber;
	}

	public void setExpectedNumber(Long expectedNumber) {
		this.expectedNumber = expectedNumber;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public BigDecimal getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(BigDecimal expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public BigDecimal getActualRevenue() {
		return actualRevenue;
	}

	public void setActualRevenue(BigDecimal actualRevenue) {
		this.actualRevenue = actualRevenue;
	}

	public BigDecimal getExpectedResults() {
		return expectedResults;
	}

	public void setExpectedResults(BigDecimal expectedResults) {
		this.expectedResults = expectedResults;
	}

	public BigDecimal getActualResults() {
		return actualResults;
	}

	public void setActualResults(BigDecimal actualResults) {
		this.actualResults = actualResults;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getPotentialSources() {
		return potentialSources;
	}

	public void setPotentialSources(String potentialSources) {
		this.potentialSources = potentialSources;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public BigDecimal getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(BigDecimal expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

//	public Long getActivityObjectTypeId() {
//		return activityObjectTypeId;
//	}
//
//	public void setActivityObjectTypeId(Long activityObjectTypeId) {
//		this.activityObjectTypeId = activityObjectTypeId;
//	}

	public String getActivityObjectTypeName() {
		return activityObjectTypeName;
	}

	public void setActivityObjectTypeName(String activityObjectTypeName) {
		this.activityObjectTypeName = activityObjectTypeName;
	}

	public String getActivityObjectType() {
		return activityObjectType;
	}

	public void setActivityObjectType(String activityObjectType) {
		this.activityObjectType = activityObjectType;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public DateTime getDob() {
		return dob;
	}

	public void setDob(DateTime dob) {
		this.dob = dob;
	}

	public String getPresenterName() {
		return presenterName;
	}

	public void setPresenterName(String presenterName) {
		this.presenterName = presenterName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getBankAcc1() {
		return bankAcc1;
	}

	public void setBankAcc1(String bankAcc1) {
		this.bankAcc1 = bankAcc1;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public Long getBonusScore() {
		return bonusScore;
	}

	public void setBonusScore(Long bonusScore) {
		this.bonusScore = bonusScore;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Long getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Long evaluate) {
		this.evaluate = evaluate;
	}

	public BigDecimal getRevenueDueYear() {
		return revenueDueYear;
	}

	public void setRevenueDueYear(BigDecimal revenueDueYear) {
		this.revenueDueYear = revenueDueYear;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public Long getRevType() {
		return revType;
	}

	public void setRevType(Long revType) {
		this.revType = revType;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public CustomerSummaryDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSummaryDto customer) {
		this.customer = customer;
	}

	public CustomerContactSummaryDto getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(CustomerContactSummaryDto customerContact) {
		this.customerContact = customerContact;
	}

}