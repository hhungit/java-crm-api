package com.bys.crm.app.dto;

import java.util.List;

import javax.validation.Valid;

public class PrivilegeDto{
	private Integer id;

	private String name;

	private String caption;

//	@Valid
//	private UserGroupDto groups;

	@Valid
	private List<PrivilegeDetailDto> details;

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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

//	public UserGroupDto getGroups() {
//		return groups;
//	}
//
//	public void setGroups(UserGroupDto groups) {
//		this.groups = groups;
//	}

	public List<PrivilegeDetailDto> getDetails() {
		return details;
	}

	public void setDetails(List<PrivilegeDetailDto> details) {
		this.details = details;
	}

}