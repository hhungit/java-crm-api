package com.bys.crm.app.dto;

import java.util.List;

import org.joda.time.DateTime;

public class AuditDto implements Comparable<AuditDto>{

	private String user;

	private Integer userId;

	private DateTime date;

	private Long action;

	private List<AuditValueDto> auditValueDto;

	private String objectType;

	private Long objectTypeId;

	private String objectTypeName;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Long getAction() {
		return action;
	}

	public void setAction(Long action) {
		this.action = action;
	}

	public List<AuditValueDto> getAuditValueDto() {
		return auditValueDto;
	}

	public void setAuditValueDto(List<AuditValueDto> auditValueDto) {
		this.auditValueDto = auditValueDto;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getObjectTypeId() {
		return objectTypeId;
	}

	public void setObjectTypeId(Long objectTypeId) {
		this.objectTypeId = objectTypeId;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public AuditDto(String user, Long action, List<AuditValueDto> auditValueDto, DateTime date) {
		this.user = user;
		this.action = action;
		this.auditValueDto = auditValueDto;
		this.date = date;
	}

	public AuditDto(String user, Long action, DateTime date, String objectType) {
		this.user = user;
		this.action = action;
		this.date = date;
		this.objectType = objectType;
	}

	public AuditDto(String user, Long action, DateTime date, String objectType, Long objectTypeId, String objectTypeName,
			Integer userId) {
		this.user = user;
		this.action = action;
		this.date = date;
		this.objectType = objectType;
		this.objectTypeId = objectTypeId;
		this.objectTypeName = objectTypeName;
		this.userId = userId;
	}

	@Override
	public int compareTo(AuditDto o) {
		return o.getDate().compareTo(this.getDate());
	}

}