package com.bys.crm.app.dto;

import java.math.BigDecimal;

public class ObjectDto {

	private String objectName;

	private String objectAddress;

	private String objectType;

	private Long objectId;
	
	private String objectEmail;
	
	private String objectCompany;
	
	private String objectWebsite;
	
	private BigDecimal owing = new BigDecimal(0);

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectAddress() {
		return objectAddress;
	}

	public void setObjectAddress(String objectAddress) {
		this.objectAddress = objectAddress;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectEmail() {
		return objectEmail;
	}

	public void setObjectEmail(String objectEmail) {
		this.objectEmail = objectEmail;
	}

	public String getObjectCompany() {
		return objectCompany;
	}

	public void setObjectCompany(String objectCompany) {
		this.objectCompany = objectCompany;
	}

	public String getObjectWebsite() {
		return objectWebsite;
	}

	public void setObjectWebsite(String objectWebsite) {
		this.objectWebsite = objectWebsite;
	}

	public BigDecimal getOwing() {
		return owing;
	}

	public void setOwing(BigDecimal owing) {
		this.owing = owing;
	}

	public ObjectDto(String objectName, String objectAddress, String objectType, Long objectId) {
		super();
		this.objectName = objectName;
		this.objectAddress = objectAddress;
		this.objectType = objectType;
		this.objectId = objectId;
	}

	public ObjectDto(String objectName, String objectAddress, String objectType, Long objectId, String email) {
		super();
		this.objectName = objectName;
		this.objectAddress = objectAddress;
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectEmail = email;
	}

	public ObjectDto(String objectName, String objectAddress, String objectType, Long objectId, String email, String company, String website, BigDecimal owing) {
		super();
		this.objectName = objectName;
		this.objectAddress = objectAddress;
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectEmail = email;
		this.objectCompany = company;
		this.objectWebsite = website;
		this.owing = owing;
	}
}