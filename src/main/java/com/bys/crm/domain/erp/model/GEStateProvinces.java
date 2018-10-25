package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "GEStateProvinces")
public class GEStateProvinces {
	@Id
	@Column(name = "GEStateProvinceID")
	private Long id;

	@Column(name = "AAStatus")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GECountryID")
	private GECountrys country;

	@Column(name = "GEStateProvinceCode")
	private String code;

	@Column(name = "GEStateProvinceName", nullable = false)
	private String name;

	public GEStateProvinces() {
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

	public GECountrys getCountry() {
		return country;
	}

	public void setCountry(GECountrys country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
