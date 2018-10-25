package com.bys.crm.app.dto;

import javax.validation.Valid;

public class BranchsDto {

	private Integer id;

	private String name;

	private String branchNumber;

	private String description;

	private String type;

	//private boolean check;

	//private Long parentId;

	private String bankCode;

	private String bankName;

	private String tax;

	private String contactLevel;

	@Valid
	private LocationDto location;

	private String status;

	public BranchsDto() {
		super();
	}
	
	public BranchsDto(Integer id) {
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

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public boolean isCheck() {
//		return check;
//	}
//
//	public void setCheck(boolean check) {
//		this.check = check;
//	}
//
//	public Long getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(Long parentId) {
//		this.parentId = parentId;
//	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContactLevel() {
		return contactLevel;
	}

	public void setContactLevel(String contactLevel) {
		this.contactLevel = contactLevel;
	}

}