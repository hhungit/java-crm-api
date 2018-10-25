package com.bys.crm.app.dto;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.joda.time.DateTime;

public class CampaignDto {
	private Long id;

	private DateTime createdDate;

	private DateTime updatedDate;

	private String name;

	@Valid
	private BranchSummaryDto branch;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	private String campaignStatus;

	private String type;

	private String campaignObject;

	private DateTime startDate;

	private DateTime completionDate;

	private String donor;

	private String goals;

	private Long expectedNumber;

	private String assignedTo;

	private BigDecimal budget;

	private BigDecimal costs;

	private BigDecimal expectedRevenue;

	private BigDecimal actualRevenue;

	private BigDecimal expectedResults;

	private BigDecimal actualResults;

	private String description;

	private String campaignNo;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
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

	public String getCampaignNo() {
		return campaignNo;
	}

	public void setCampaignNo(String campaignNo) {
		this.campaignNo = campaignNo;
	}

}