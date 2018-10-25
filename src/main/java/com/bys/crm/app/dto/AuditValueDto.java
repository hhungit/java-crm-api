package com.bys.crm.app.dto;

public class AuditValueDto {

	private String field;

	private String beforeValue;

	private String afterValue;

	public AuditValueDto(String field, String beforeValue, String afterValue) {
		this.field = field;
		this.beforeValue = beforeValue;
		this.afterValue = afterValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

}