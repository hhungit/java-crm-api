package com.bys.crm.domain.erp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
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
import com.bys.crm.util.DateTimeUtil;

@Entity
@Audited
@Table(name = "ARCampaigns")
//@Where(clause = "AAStatus = 'Alive'")
public class ARCampaigns extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCampaignID", columnDefinition = "int", nullable = false)
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

	@Column(name = "ARCampaignName", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HRGroupID", columnDefinition = "int")
	private HRGroups employeeGroup;

	@Column(name = "ARCampaignStatus", columnDefinition = "nvarchar(100)")
	private String campaignStatus;

	@Column(name = "ARCampaignType", columnDefinition = "nvarchar(100)")
	private String type;

	@Column(name = "ARCampaignObject", columnDefinition = "nvarchar(200)")
	private String campaignObject;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCampaignStartDate")
	private DateTime startDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCampaignCompletionDate")
	private DateTime completionDate;

	@Column(name = "ARCampaignDonor", columnDefinition = "nvarchar(100)")
	private String donor;

	@Column(name = "ARCampaignGoals", columnDefinition = "nvarchar(200)")
	private String goals;

	@Column(name = "ARCampaignExpectedNumber")
	private Long expectedNumber;

	@Column(name = "ARCampaignAssignedTo", columnDefinition = "nvarchar(50)")
	private String assignedTo;

	@Column(name = "ARCampaignBudget", columnDefinition = "decimal(18, 5)")
	private BigDecimal budget;

	@Column(name = "ARCampaignActualCosts", columnDefinition = "decimal(18, 5)")
	private BigDecimal costs;

	@Column(name = "ARCampaignExpectedRevenue", columnDefinition = "decimal(18, 5)")
	private BigDecimal expectedRevenue;

	@Column(name = "ARCampaignActualRevenue", columnDefinition = "decimal(18, 5)")
	private BigDecimal actualRevenue;

	@Column(name = "ARCampaignExpectedResults", columnDefinition = "decimal(18, 5)")
	private BigDecimal expectedResults;

	@Column(name = "ARCampaignActualResults", columnDefinition = "decimal(18, 5)")
	private BigDecimal actualResults;

	@Column(name = "ARCampaignDescription", columnDefinition = "nvarchar(512)")
	private String description;

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

	@Column(name = "ARCampaignNumber", columnDefinition = "varchar(100)")
	@NotAudited
	private String campaignNo;

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

	public ARCampaigns() {
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

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCampaignNo() {
		return campaignNo;
	}

	public void setCampaignNo(String campaignNo) {
		this.campaignNo = campaignNo;
	}

}
