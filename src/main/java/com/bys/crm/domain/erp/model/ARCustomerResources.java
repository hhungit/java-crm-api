package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ARCustomerResources")
public class ARCustomerResources extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCustomerResourceID", columnDefinition = "int", nullable = false)
	private Long id;
	
	@Column(name = "AAStatus")
	private String status;
	
	@Column(name = "ARCustomerResourceNo", nullable = false, columnDefinition = "nvarchar(4000)")
	private String no;
	
	@Column(name = "ARCustomerResourceName", nullable = false, columnDefinition = "nvarchar(4000)")
	private String name;
	
	@Column(name = "ARCustomerResourceDesc", nullable = false, columnDefinition = "nvarchar(4000)")
	private String desc;

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
