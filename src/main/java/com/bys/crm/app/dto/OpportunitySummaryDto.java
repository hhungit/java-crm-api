package com.bys.crm.app.dto;

import javax.validation.constraints.Size;

public class OpportunitySummaryDto {
	private Long id;

	@Size(max = 50, message = "name should not exceed 50 characters")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}