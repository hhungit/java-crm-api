package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GENumbering")
public class GENumbering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GENumberingID")
	private Long id;
	
	@Column(name = "GENumberingName")
	private String name;
	
	@Column(name = "GENumberingPrefix")
	private String prefix;
	
	@Column(name = "GENumberingStart")
	private int start;
	
	@Column(name = "GENumberingLength")
	private int length;
	
	@Column(name = "GENumberingPrefixHaveYear")
	private Boolean  haveYear;

	@Column(name = "FK_BRBranchID")
	private int branchId;

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Boolean isHaveYear() {
		return haveYear;
	}

	public void setHaveYear(Boolean haveYear) {
		this.haveYear = haveYear;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

}
