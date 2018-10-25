package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ICProducts")
public class ICProducts extends DomainEntity<Long> {
	@Id
	@Column(name = "ICProductID", nullable = false)
	private Long id;

	@Column(name = "ICProductName")
	private String productName;

	@Column(name = "ICProductNo")
	private String productNo;
	
	@Column(name = "ICProductDesc")
	private String productDesc;

	@Column(name = "AAStatus")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
}
