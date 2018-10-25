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
@Table(name = "GEDistricts")
public class GEDistricts {
	@Id
	@Column(name = "GEDistrictID")
	private Long id;

	@Column(name = "AAStatus")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GEStateProvinceID")
	private GEStateProvinces province;

	@Column(name = "GEDistrictCode")
	private String code;

	@Column(name = "GEDistrictName", nullable = false)
	private String name;

	public GEDistricts() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GEStateProvinces getProvince() {
		return province;
	}

	public void setProvince(GEStateProvinces province) {
		this.province = province;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
