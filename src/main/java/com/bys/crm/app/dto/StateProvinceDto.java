package com.bys.crm.app.dto;

import javax.validation.Valid;

public class StateProvinceDto {

	private Long id;

	private String code;

	private String name;

	@Valid
	private CountryDto country;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

}