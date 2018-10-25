package com.bys.crm.app.dto;

public class BranchSummaryDto {

	private Integer id;

	private String name;

	public BranchSummaryDto() {
		super();
	}

	public BranchSummaryDto(Integer id) {
		this.id = id;
	}

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

}