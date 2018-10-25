package com.bys.crm.app.dto;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;

public class ActivityDto implements Comparable<ActivityDto>{
	private Long id;

	private String createdUser;

	private DateTime createdDate;

	private String updatedUser;

	private DateTime updatedDate;

	private String name;

	@Valid
	private BranchSummaryDto branch;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;

	private String employeeNo;

	private String assignedTo;

	private DateTime startDate;

	private DateTime endDate;

	private String activityStatus;

	private String eventType;

	private String address;

	private String activityType;

	private Long activityObjectTypeId;

	private String activityObjectTypeName;

	private String description;

	private String activityObjectType;

	private Boolean isAssigned;

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	@Valid
	private List<EmployeeSummaryDto> employees;

	@Valid
	private List<GroupSummaryDto> employeeGroups;

	private Boolean isShare;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Long getActivityObjectTypeId() {
		return activityObjectTypeId;
	}

	public void setActivityObjectTypeId(Long activityObjectTypeId) {
		this.activityObjectTypeId = activityObjectTypeId;
	}

	public String getActivityObjectTypeName() {
		return activityObjectTypeName;
	}

	public void setActivityObjectTypeName(String activityObjectTypeName) {
		this.activityObjectTypeName = activityObjectTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivityObjectType() {
		return activityObjectType;
	}

	public void setActivityObjectType(String activityObjectType) {
		this.activityObjectType = activityObjectType;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public List<EmployeeSummaryDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeSummaryDto> employees) {
		this.employees = employees;
	}

	public List<GroupSummaryDto> getEmployeeGroups() {
		return employeeGroups;
	}

	public void setEmployeeGroups(List<GroupSummaryDto> employeeGroups) {
		this.employeeGroups = employeeGroups;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	@Override
	public int compareTo(ActivityDto o) {
		return o.getStartDate().compareTo(this.getStartDate());
	}
}