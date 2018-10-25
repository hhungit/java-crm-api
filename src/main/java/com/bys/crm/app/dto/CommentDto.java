package com.bys.crm.app.dto;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;


public class CommentDto {
	private Long id;

	private DateTime createdDate;

	private DateTime updatedDate;

	private String type;

	private Long objectId;

	private String description;

	private DateTime date;

	private Long parentId;

	@Valid
	private EmployeeSummaryDto employee;
	
	private List<CommentDto> children;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public List<CommentDto> getChildren() {
		return children;
	}

	public void setChildren(List<CommentDto> children) {
		this.children = children;
	}

}