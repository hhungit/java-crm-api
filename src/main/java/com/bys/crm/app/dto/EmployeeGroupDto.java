package com.bys.crm.app.dto;

import javax.validation.Valid;

public class EmployeeGroupDto {

	private Long id;

	@Valid
	private EmployeeSummaryDto employees;

	@Valid
	private GroupDto groups;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeSummaryDto getEmployees() {
		return employees;
	}

	public void setEmployees(EmployeeSummaryDto employees) {
		this.employees = employees;
	}

	public GroupDto getGroups() {
		return groups;
	}

	public void setGroups(GroupDto groups) {
		this.groups = groups;
	}

}
