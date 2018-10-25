package com.bys.crm.domain.erp.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.converter.GenderConverter;
import com.bys.crm.util.DateTimeUtil;

@Entity
@Audited
@Table(name = "ARCustomers")
//@Where(clause = "AAStatus = 'Alive'")
public class ARCustomers extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCustomerID", columnDefinition = "int", nullable = false)
	private Long id;
	
	@Column(name = "ARGender")
	@Convert(converter = GenderConverter.class)
	private Gender gender;
	
	@Column(name = "ARCustomerName", nullable = false, columnDefinition = "nvarchar(4000)")
	private String name;
	
	@Column(name = "ARCustomerContactCellPhone1", columnDefinition = "nvarchar(50)")
	private String phone;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCustomerContactBirthday")
	private DateTime dob;
	
	@Column(name = "ARCustomerContactAddressLine1", columnDefinition = "nvarchar(2000)")
	private String address;
	
	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "AACreatedUser", columnDefinition = "nvarchar(50)")
	private String createdUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;

	@Column(name = "AAUpdatedUser", columnDefinition = "nvarchar(50)")
	private String updatedUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime updatedDate;

	@Column(name = "ARCustomerActiveCheck")
	private boolean check;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_GELocationID")
	@NotAudited
	private GELocations location;
	
	@Column(name = "ARCustomerContactEmail1", columnDefinition = "nvarchar(1000)")
	private String email;
	
	@Column(name = "ARCustomerNo", columnDefinition = "nvarchar(50)")
	private String customerNumber;
	
	@Column(name = "ARCustomerTypeCombo", columnDefinition = "nvarchar(50)")
	private String customerType;
	
	@Column(name = "ARCustomerContactName", columnDefinition = "nvarchar(2000)")
	private String presenterName;
	
	@Column(name = "ARCustomerContactPhone1", columnDefinition = "varchar(50)")
	private String companyPhone;
	
	@Column(name = "ARCustomerTaxNumber", columnDefinition = "varchar(50)")
	private String taxNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;
	
	@Column(name = "ARCustomerBonusScore")
	private Long bonusScore;
	
	@Column(name = "ARCustomerAssignedTo", columnDefinition = "nvarchar(2000)")
	private String assignedTo;
	
	@Column(name = "ARCustomerBusiness", columnDefinition = "nvarchar(2000)")
	private String business;
	
	@Column(name = "ARCustomerClassify", columnDefinition = "nvarchar(2000)")
	private String classify;
	
	@Column(name = "ARCustomerContactEmail2", columnDefinition = "nvarchar(2000)")
	private String email2;
	
	@Column(name = "ARCustomerContactFax", columnDefinition = "nvarchar(50)")
	private String fax;
	
	@Column(name = "ARCustomerContactInformation", columnDefinition = "nvarchar(4000)")
	private String information;
	
	@Column(name = "ARCustomerEvaluate")
	private Long evaluate;
	
	@Column(name = "ARCustomerRevenueDueYear", columnDefinition = "decimal(18, 5)")
	private BigDecimal revenueDueYear;
	
	@Column(name = "ARCustomerStockCode", columnDefinition = "varchar(500)")
	private String stockCode;
	
	@Column(name = "ARCustomerContactPhone", columnDefinition = "nvarchar(50)")
	private String tel1;
	
	@Column(name = "ARCustomerContactCellPhone", columnDefinition = "nvarchar(50)")
	private String tel2;
	
	@Column(name = "ARCustomerWebsite", columnDefinition = "nvarchar(2000)")
	private String website;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HRGroupID", columnDefinition = "int")
	private HRGroups employeeGroup;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARProspectCustomerID", columnDefinition = "int")
	@NotAudited
	private ARProspectCustomers prospect;
	
	@Column(name = "ARCustomerContactAddressCity", columnDefinition = "nvarchar(2000)")
	private String city;
	
	@Column(name = "ARCustomerContactAddressCountry", columnDefinition = "nvarchar(2000)")
	private String country;
	
	@Column(name = "ARCustomerContactAddressDistrict", columnDefinition = "nvarchar(2000)")
	private String district;
	
	@Column(name = "ARCustomerGroup", columnDefinition = "nvarchar(500)")
	private String group;

	@Column(name = "REV", insertable = false , updatable = false)
	private Long rev;

	@Column(name = "REVTYPE", insertable = false , updatable = false)
	private Long revType;

	@Column(name = "FK_HREmployeeID", insertable = false , updatable = false)
	private Integer employeeId;

	@Column(name = "FK_HRGroupID", insertable = false , updatable = false)
	private Integer employeeGroupId;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCustomerCompanyEstablishmentDay", columnDefinition = "datetime")
	private DateTime companyEstablishmentDay;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<ARCustomerContactGroups> customerContactGroups;

	@Column(name = "CreatedUserID", columnDefinition = "int")
	private Integer createdUserId;
	
	@Column(name = "UpdatedUserID", columnDefinition = "int")
	private Integer updatedUserId;

	@Column(name = "ARCustomerChangedUser", columnDefinition = "nvarchar(50)")
	private String changedUser;

	@Column(name = "ARCustomerDesc")
	@NotAudited
	private String desc;

	@Column(name = "ARCustomerGroupCombo")
	@NotAudited
	private String customerGroup;

	@Column(name = "ARCustomerCreditLimit")
	@NotAudited
	private BigDecimal creditLimit;

	@Column(name = "ARCustomerOwing")
	@NotAudited
	private BigDecimal owing;

	@Column(name = "FK_ARCustomerTypeAccountConfigID")
	@NotAudited
	private Integer typeAccountConfigID;

	@NotAudited
	@Column(name = "ARCustomerLunarBirthday", columnDefinition = "nvarchar(50)")
	private String lunarBirthday;

	public ARCustomers() {
		this.status = AAStatus.Alive.name();
		this.check = true;
		this.creditLimit = new BigDecimal(0);
		this.typeAccountConfigID = 0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public GELocations getLocation() {
		return location;
	}

	public void setLocation(GELocations location) {
		this.location = location;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
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

	public Long getBonusScore() {
		return bonusScore;
	}

	public void setBonusScore(Long bonusScore) {
		this.bonusScore = bonusScore;
	}

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
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

	public HREmployees getEmployee() {
		return employee;
	}

	public void setEmployee(HREmployees employee) {
		this.employee = employee;
	}

	public ARProspectCustomers getProspect() {
		return prospect;
	}

	public void setProspect(ARProspectCustomers prospect) {
		this.prospect = prospect;
	}

	public HRGroups getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(HRGroups employeeGroup) {
		this.employeeGroup = employeeGroup;
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
	public DateTime getCreatedDateChartKeyByDay(){
		return DateTimeUtil.toDateTimeAtStartOfDay(this.getCreatedDate().getMillis());
	}
	
	public DateTime getCreatedDateChartKeyByMonth(){
		return DateTimeUtil.toDateTimeAtStartOfDay(this.getCreatedDate().getMillis()).withDayOfMonth(1);
	}
	
	public DateTime getCreatedDateChartKeyByYear(){
		return DateTimeUtil.toDateTimeAtStartOfDay(this.getCreatedDate().getMillis()).withDayOfMonth(1).withMonthOfYear(1);
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}

	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
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

	public Set<ARCustomerContactGroups> getCustomerContactGroups() {
		return customerContactGroups;
	}

	public void setCustomerContactGroups(Set<ARCustomerContactGroups> customerContactGroups) {
		this.customerContactGroups = customerContactGroups;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Integer getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
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

	public Integer getTypeAccountConfigID() {
		return typeAccountConfigID;
	}

	public void setTypeAccountConfigID(Integer typeAccountConfigID) {
		this.typeAccountConfigID = typeAccountConfigID;
	}

	public String getLunarBirthday() {
		return lunarBirthday;
	}

	public void setLunarBirthday(String lunarBirthday) {
		this.lunarBirthday = lunarBirthday;
	}

}
