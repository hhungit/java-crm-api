package com.bys.crm.app.dto;

public class GroupDto {

	private Integer id;

	private String name;

	private String description;

//	private Set<EmployeeSummaryDto> employees = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<EmployeeSummaryDto> getEmployees() {
//		return employees;
//	}
//
//	public void setEmployees(Set<EmployeeSummaryDto> employees) {
//		this.employees = employees;
//	}

}
