package com.bys.crm.app.dto;

import javax.validation.constraints.Size;

public class EmployeeSummaryDto {
	private Integer id;
	
	@Size(max = 150, message = "name should not exceed 150 characters")
	private String name;

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

	public EmployeeSummaryDto() {
		super();
	}

	public EmployeeSummaryDto(Integer id) {
		this.id = id;
	}

}
