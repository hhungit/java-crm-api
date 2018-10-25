package com.bys.crm.app.dto;

import java.util.List;

import javax.validation.Valid;

public class PrivilegeGroupDto {

	private Integer id;

	private String name;

	private String description;

	@Valid
	private List<PrivilegeDto> privileges;

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

	public List<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}

}
