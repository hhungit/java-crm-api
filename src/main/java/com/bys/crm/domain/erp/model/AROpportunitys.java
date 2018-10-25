package com.bys.crm.domain.erp.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "AROpportunitys")
//@Where(clause = "AAStatus = 'Alive'")
public class AROpportunitys extends DomainEntity<Long> {
	@Id
	@Column(name = "AROpportunityID", nullable = false)
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

	@Column(name = "AROpportunityName", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARCustomerID", columnDefinition = "int")
	private ARCustomers customer;

//	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "FK_ARCustomerContactID", columnDefinition = "int")
//	private ARCustomerContacts customerContact;

	@Column(name = "AROpportunityClassify", columnDefinition = "nvarchar(100)")
	private String classify;

	@Column(name = "AROpportunityPotentialSources", columnDefinition = "nvarchar(100)")
	private String potentialSources;

	@Column(name = "AROpportunityAmount", columnDefinition = "decimal(18, 5)")
	private BigDecimal amount;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AROpportunityCompletionDate")
	private DateTime completionDate;

	@Column(name = "AROpportunityStep", columnDefinition = "nvarchar(100)")
	private String step;

	@Column(name = "AROpportunityProbability", columnDefinition = "decimal(18, 5)")
	private BigDecimal probability;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCampaignID", columnDefinition = "int")
	private ARCampaigns campaign;

	@Column(name = "AROpportunityExpectedValue", columnDefinition = "decimal(18, 5)")
	private BigDecimal expectedValue;

	@Column(name = "AROpportunityAssignedTo", columnDefinition = "nvarchar(50)")
	private String assignedTo;

	@Column(name = "AROpportunityDescription", columnDefinition = "nvarchar(512)")
	private String description;

	@Column(name = "AROpportunityCustomerName", columnDefinition = "nvarchar(50)")
	private String customerNm;

	@Column(name = "AROpportunityContactName", columnDefinition = "nvarchar(50)")
	private String contactName;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HRGroupID", columnDefinition = "int")
	private HRGroups employeeGroup;
	
	@Column(name = "REV", insertable = false , updatable = false)
	private Long rev;

	@Column(name = "REVTYPE", insertable = false , updatable = false)
	private Long revType;

	@Column(name = "FK_HREmployeeID", insertable = false , updatable = false)
	private Integer employeeId;

	@Column(name = "FK_HRGroupID", insertable = false , updatable = false)
	private Integer employeeGroupId;

	@Column(name = "CreatedUserID", columnDefinition = "int")
	private Integer createdUserId;
	
	@Column(name = "UpdatedUserID", columnDefinition = "int")
	private Integer updatedUserId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "opportunity", cascade = CascadeType.ALL)
	private Set<AROpportunityContactGroups> opportunityContactGroups;

	@Column(name = "IsShare", columnDefinition = "bit")
	@NotAudited
	private Boolean isShare;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARCustomerResourceID")
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ARCustomerResources customerResource;

	public AROpportunitys() {
		this.status = AAStatus.Alive.name();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
	}

//	public ARCustomerContacts getCustomerContact() {
//		return customerContact;
//	}
//
//	public void setCustomerContact(ARCustomerContacts customerContact) {
//		this.customerContact = customerContact;
//	}

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

	public DateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(DateTime completionDate) {
		this.completionDate = completionDate;
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

//	public String getStrategy() {
//		return strategy;
//	}
//
//	public void setStrategy(String strategy) {
//		this.strategy = strategy;
//	}

	public BigDecimal getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(BigDecimal expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ARCustomers getCustomer() {
		return customer;
	}

	public void setCustomer(ARCustomers customer) {
		this.customer = customer;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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
	public ARCampaigns getCampaign() {
		return campaign;
	}

	public void setCampaign(ARCampaigns campaign) {
		this.campaign = campaign;
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

	public Set<AROpportunityContactGroups> getOpportunityContactGroups() {
		return opportunityContactGroups;
	}

	public void setOpportunityContactGroups(Set<AROpportunityContactGroups> opportunityContactGroups) {
		this.opportunityContactGroups = opportunityContactGroups;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public ARCustomerResources getCustomerResource() {
		return customerResource;
	}

	public void setCustomerResource(ARCustomerResources customerResource) {
		this.customerResource = customerResource;
	}

}
