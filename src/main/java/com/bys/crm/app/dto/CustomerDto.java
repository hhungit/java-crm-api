package com.bys.crm.app.dto;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

public class CustomerDto {

	private Long id;

	private String customerNumber;

	private boolean check;

	private String gender;

	@Size(max = 100, message = "name should not exceed 100 characters")
	private String name;

	private String phone;

	private DateTime dob;

	@Email(message = "email is invalid")
	@Size(max = 50, message = "email should not exceed 50 characters")
	private String email;

	@Size(max = 50, message = "presenterName should not exceed 50 characters")
	private String presenterName;

	private String companyPhone;

	@Size(max = 50, message = "taxNumber should not exceed 50 characters")
	private String taxNumber;

	private String bankAcc1;

	private String address;

	private String address3;

	private Long bonusScore;

	@Valid
	private LocationDto location;

	private String assignedTo;

	private String business;

	private String classify;

	private String email2;

	private String fax;

	private String information;

	private Long evaluate;

	private BigDecimal revenueDueYear;

	private String stockCode;

	private String tel1;

	private String tel2;

	private String website;

	private String createdUser;

	private DateTime createdDate;

	private String updatedUser;

	private DateTime updatedDate;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	@Valid
	private ProspectCustomerSummaryDto prospect;

	private String employeeNo;

	private String city;

	private String country;

	private String district;

	private String group;

	@Valid
	private BranchSummaryDto branch;

	@NotNull(message = "Customer Type is must not null")
	private String customerType;

	private DateTime companyEstablishmentDay;

	private Set<CustomerContactDto> contacts;

	private Set<CustomerContactGroupDto> customerContactGroups;

	private String changedUser;

	private String desc;

	private String customerGroup;

	private BigDecimal creditLimit;

	private BigDecimal owing;

	private String lunarBirthday;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public Long getBonusScore() {
		return bonusScore;
	}

	public void setBonusScore(Long bonusScore) {
		this.bonusScore = bonusScore;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
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

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public DateTime getCompanyEstablishmentDay() {
		return companyEstablishmentDay;
	}

	public void setCompanyEstablishmentDay(DateTime companyEstablishmentDay) {
		this.companyEstablishmentDay = companyEstablishmentDay;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Set<CustomerContactDto> getContacts() {
		return contacts;
	}

	public void setContacts(Set<CustomerContactDto> contacts) {
		this.contacts = contacts;
	}

	public Set<CustomerContactGroupDto> getCustomerContactGroups() {
		return customerContactGroups;
	}

	public void setCustomerContactGroups(Set<CustomerContactGroupDto> customerContactGroups) {
		this.customerContactGroups = customerContactGroups;
	}

	public String getChangedUser() {
		return changedUser;
	}

	public void setChangedUser(String changedUser) {
		this.changedUser = changedUser;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BigDecimal getOwing() {
		return owing;
	}

	public void setOwing(BigDecimal owing) {
		this.owing = owing;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getLunarBirthday() {
		return lunarBirthday;
	}

	public void setLunarBirthday(String lunarBirthday) {
		this.lunarBirthday = lunarBirthday;
	}

}