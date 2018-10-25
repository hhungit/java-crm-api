package com.bys.crm.domain.erp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ARProspectCustomers")
public class ARProspectCustomers extends DomainEntity<Long> {
	@Id
	@Column(name = "ARProspectCustomerID", columnDefinition = "int", nullable = false)
	private Long id;

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

	@Column(name = "ARProspectCustomerStatus", columnDefinition = "varchar(50)")
	private String potentialStatus;

	@Column(name = "ARProspectCustomerName", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@Column(name = "ARProspectCustomerTitle", columnDefinition = "nvarchar(50)")
	private String title;

	@Column(name = "ARProspectCustomerFirstName", columnDefinition = "nvarchar(50)")
	private String firstName;

	@Column(name = "ARProspectCustomerLastName", columnDefinition = "nvarchar(50)")
	private String lastName;

	@Column(name = "ARProspectCustomerTel", columnDefinition = "varchar(50)")
	private String phone;

	@Column(name = "ARProspectCustomerCellPhone", columnDefinition = "varchar(50)")
	private String cellPhone;

	@Column(name = "ARProspectCustomerEmail", columnDefinition = "varchar(50)")
	private String email;

	@Column(name = "ARProspectCustomerWebsite", columnDefinition = "varchar(50)")
	private String website;

	@Column(name = "ARProspectCustomerCompany", columnDefinition = "nvarchar(200)")
	private String company;

	@Column(name = "ARProspectCustomerPotentialSource", columnDefinition = "nvarchar(200)")
	private String potentialSource;

	@Column(name = "ARProspectCustomerAddress", columnDefinition = "nvarchar(200)")
	private String address;

	@Column(name = "ARProspectCustomerRate", columnDefinition = "nvarchar(50)")
	private String rate;

	@Column(name = "ARProspectCustomerDescription", columnDefinition = "nvarchar(512)")
	private String description;

	@Column(name = "ARProspectCustomerAssign", columnDefinition = "nvarchar(50)")
	private String assign;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;

	@Column(name = "ARProspectCustomerBusiness", columnDefinition = "nvarchar(100)")
	private String business;

	@Column(name = "ARPropectRevenueDueYear", columnDefinition = "decimal(18, 5)")
	private BigDecimal revenue;

	@Column(name = "ARProspectCustomerAddressCountry", columnDefinition = "nvarchar(100)")
	private String country;

	@Column(name = "ARProspectCustomerAddressCity", columnDefinition = "nvarchar(100)")
	private String city;

	@Column(name = "ARProspectCustomerAddressDistrict", columnDefinition = "nvarchar(100)")
	private String district;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HRGroupID", columnDefinition = "int")
	private HRGroups employeeGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GELocationID", columnDefinition = "int")
	@NotAudited
	private GELocations location;

	@Column(name = "FK_HREmployeeID", insertable = false , updatable = false)
	private Integer employeeId;

	@Column(name = "FK_HRGroupID", insertable = false , updatable = false)
	private Integer employeeGroupId;

	@Column(name = "REV", insertable = false , updatable = false)
	private Long rev;

	@Column(name = "REVTYPE", insertable = false , updatable = false)
	private Long revType;

	@Column(name = "CreatedUserID", columnDefinition = "int")
	private Integer createdUserId;
	
	@Column(name = "UpdatedUserID", columnDefinition = "int")
	private Integer updatedUserId;

	@Column(name = "ARProspectObjectType", columnDefinition = "varchar(100)")
	private String objectType;

	@Column(name = "ARProspectObjectID", columnDefinition = "int")
	private Integer objectId;

	@NotAudited
	@Column(name = "ARProspectCustomerGender")
	@Convert(converter = GenderConverter.class)
	private Gender gender;

	@NotAudited
	@Column(name = "ARProspectCustomerLunarBirthday", columnDefinition = "nvarchar(50)")
	private String lunarBirthday;

	@NotAudited
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARProspectCustomerBirthday")
	private DateTime dob;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARCustomerResourceID")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ARCustomerResources customerResource;
	
	public ARProspectCustomers() {
		this.status = AAStatus.Alive.name();
		this.name = "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
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

	public HREmployees getEmployee() {
		return employee;
	}

	public void setEmployee(HREmployees employee) {
		this.employee = employee;
	}

	public HRGroups getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(HRGroups employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public GELocations getLocation() {
		return location;
	}

	public void setLocation(GELocations location) {
		this.location = location;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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

	public ARCustomerResources getCustomerResource() {
		return customerResource;
	}

	public void setCustomerResource(ARCustomerResources customerResource) {
		this.customerResource = customerResource;
	}

}
