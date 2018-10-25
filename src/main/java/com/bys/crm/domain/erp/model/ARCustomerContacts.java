package com.bys.crm.domain.erp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.bys.crm.util.DateTimeUtil;

@Entity
@Audited
@Table(name = "ARCustomerContacts")
//@Where(clause = "AAStatus = 'Alive'")
public class ARCustomerContacts extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCustomerContactID", columnDefinition = "int", nullable = false)
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

	@Column(name = "ARCustomerContactName", nullable = false, columnDefinition = "nvarchar(100)")
	private String name;

	@Column(name = "FK_ARCustomerID", columnDefinition = "int")
	private Long customerId;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCustomerContactBirthday")
	private DateTime birthday;

	@Column(name = "ARCustomerContactFirstName", columnDefinition = "nvarchar(50)")
	private String firstName;

	@Column(name = "ARCustomerContactLastName", columnDefinition = "nvarchar(50)")
	private String lastName;

	@Column(name = "ARCustomerContactTitle", columnDefinition = "nvarchar(50)")
	private String title;

	@Column(name = "ARCustomerContactEmail1", columnDefinition = "nvarchar(100)")
	private String email;

	@Column(name = "ARCustomerContactWebsite", columnDefinition = "nvarchar(100)")
	private String website;

	@Column(name = "ARCustomerContactPhone")
	private String phone;

	@Column(name = "ARContactType", columnDefinition = "nvarchar(100)")
	private String type;

	@Column(name = "ARCustomerContactInformation", columnDefinition = "nvarchar(510)")
	private String information;

	@Column(name = "ARCustomerContactAddress", columnDefinition = "nvarchar(200)")
	private String address;

	@Column(name = "ARCustomerContactAssistant", columnDefinition = "nvarchar(100)")
	private String assistant;

	@Column(name = "ARCustomerContactAssistantPhone")
	private String assistantPhone;

	@Column(name = "ARCustomerContactCellularPhone")
	private String cellularPhone;

	@Column(name = "ARCustomerContactHomePhone")
	private String homePhone;

	@Column(name = "ARCustomerContactImage", columnDefinition = "varbinary(MAX)")
	private byte[] image;

	@Column(name = "ARCustomerContactJobTitle", columnDefinition = "nvarchar(100)")
	private String jobTitle;

	@Column(name = "ARCustomerContactPotentialSource", columnDefinition = "nvarchar(100)")
	private String potentialSource;

	@Column(name = "ARCustomerContactSecondaryPhone")
	private String secondaryPhone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARProspectCustomerID", columnDefinition = "int")
	@NotAudited
	private ARProspectCustomers prospect;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HRGroupID", columnDefinition = "int")
	private HRGroups employeeGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GELocationID")
	@NotAudited
	private GELocations location;

	@Column(name = "ARCustomerContactDepartment", columnDefinition = "nvarchar(50)")
	private String department;

	@Column(name = "ARCustomerContactAssignedTo", columnDefinition = "nvarchar(50)")
	private String assignedTo;

	@Column(name = "ARCustomerContactAddressDistrict", columnDefinition = "nvarchar(50)")
	private String district;

	@Column(name = "ARCustomerContactAddressCity", columnDefinition = "nvarchar(50)")
	private String city;

	@Column(name = "ARCustomerContactAddressCountry", columnDefinition = "nvarchar(50)")
	private String country;

	@Column(name = "ARCustomerContactAddress2", columnDefinition = "nvarchar(200)")
	private String address2;

	@Column(name = "ARCustomerContactAddressDistrict2", columnDefinition = "nvarchar(50)")
	private String district2;

	@Column(name = "ARCustomerContactAddressCity2", columnDefinition = "nvarchar(50)")
	private String city2;

	@Column(name = "ARCustomerContactAddressCountry2", columnDefinition = "nvarchar(50)")
	private String country2;

	@Column(name = "REV", insertable = false, updatable = false)
	private Long rev;

	@Column(name = "REVTYPE", insertable = false, updatable = false)
	private Long revType;

	@Column(name = "FK_HREmployeeID", insertable = false , updatable = false)
	private Integer employeeId;

	@Column(name = "FK_HRGroupID", insertable = false , updatable = false)
	private Integer employeeGroupId;

	@Column(name = "CreatedUserID", columnDefinition = "int")
	private Integer createdUserId;
	
	@Column(name = "UpdatedUserID", columnDefinition = "int")
	private Integer updatedUserId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact", cascade = CascadeType.ALL)
	private Set<ARCustomerContactGroups> customerContactGroups;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARCustomerResourceID")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ARCustomerResources customerResource;

	public ARCustomerContacts() {
		this.status = AAStatus.Alive.name();
		this.name = "";
		this.customerId = 0L;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
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

	public GELocations getLocation() {
		return location;
	}

	public void setLocation(GELocations location) {
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

	public Set<ARCustomerContactGroups> getCustomerContactGroups() {
		return customerContactGroups;
	}

	public void setCustomerContactGroups(Set<ARCustomerContactGroups> customerContactGroups) {
		this.customerContactGroups = customerContactGroups;
	}

	public ARCustomerResources getCustomerResource() {
		return customerResource;
	}

	public void setCustomerResource(ARCustomerResources customerResource) {
		this.customerResource = customerResource;
	}
		
}
