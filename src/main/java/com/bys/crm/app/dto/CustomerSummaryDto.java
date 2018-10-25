package com.bys.crm.app.dto;

public class CustomerSummaryDto {

	private Long id;

	private String name;

	private String tel1;

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

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public CustomerSummaryDto() {
		super();
	}

}