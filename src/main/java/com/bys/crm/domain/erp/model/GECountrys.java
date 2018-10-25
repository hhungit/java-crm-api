package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "GECountrys")
public class GECountrys extends DomainEntity<Long>{
	@Id
	@Column(name = "GECountryID")
	private Long id;

	@Column(name = "AAStatus")
	private String status;

	@Column(name = "GECountryCode", nullable = false)
	private String code;

	@Column(name = "GECountryName", nullable = false)
	private String name;

	public GECountrys() {
		this.status = AAStatus.Alive.name();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
