package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "GELocations")
public class GELocations {
	@Id
	@Column(name = "GELocationID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "GELocationName")
	private String name;
	
	@Column(name = "GELocationType")
	private String type;
	
	@Column(name = "AAStatus")
	@Enumerated(EnumType.STRING)
	private AAStatus status;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GELocationParentID")
	private GELocations parent;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GELocations getParent() {
		return parent;
	}

	public void setParent(GELocations parent) {
		this.parent = parent;
	}

	public AAStatus getStatus() {
		return status;
	}

	public void setStatus(AAStatus status) {
		this.status = status;
	}
	
}
