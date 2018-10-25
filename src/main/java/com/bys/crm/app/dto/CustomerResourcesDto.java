package com.bys.crm.app.dto;

public class CustomerResourcesDto {

	private Long id;
	private String no;
	private String name;
	private String desc;
	
	public CustomerResourcesDto() {
		super();
	}

	public CustomerResourcesDto(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}	
}
