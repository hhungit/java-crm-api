package com.bys.crm.app.dto;

public class LocationDto {

	private Integer id;

	private String name;

	private LocationDto parent;

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

	public LocationDto getParent() {
		return parent;
	}

	public void setParent(LocationDto parent) {
		this.parent = parent;
	}
}