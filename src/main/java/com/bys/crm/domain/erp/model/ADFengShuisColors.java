package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADFengShuisColors")
public class ADFengShuisColors extends DomainEntity<Long> {
	@Id
	@Column(name = "ADFengShuisColorID", columnDefinition = "int", nullable = false)
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "ADFengShuisColorLife", columnDefinition = "nvarchar(256)")
	private String fengShuisColorLife;

	@Column(name = "ADFengShuisColorMutual", columnDefinition = "nvarchar(256)")
	private String fengShuisColorMutual;

	@Column(name = "ADFengShuisColorHarmony", columnDefinition = "nvarchar(256)")
	private String fengShuisColorHarmony;

	@Column(name = "ADFengShuisColorFightDown", columnDefinition = "nvarchar(256)")
	private String fengShuisColorFightDown;

	@Column(name = "ADFengShuisColorConflict", columnDefinition = "nvarchar(256)")
	private String fengShuisColorConflict;

	public ADFengShuisColors() {
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

	public String getFengShuisColorLife() {
		return fengShuisColorLife;
	}

	public void setFengShuisColorLife(String fengShuisColorLife) {
		this.fengShuisColorLife = fengShuisColorLife;
	}

	public String getFengShuisColorMutual() {
		return fengShuisColorMutual;
	}

	public void setFengShuisColorMutual(String fengShuisColorMutual) {
		this.fengShuisColorMutual = fengShuisColorMutual;
	}

	public String getFengShuisColorHarmony() {
		return fengShuisColorHarmony;
	}

	public void setFengShuisColorHarmony(String fengShuisColorHarmony) {
		this.fengShuisColorHarmony = fengShuisColorHarmony;
	}

	public String getFengShuisColorFightDown() {
		return fengShuisColorFightDown;
	}

	public void setFengShuisColorFightDown(String fengShuisColorFightDown) {
		this.fengShuisColorFightDown = fengShuisColorFightDown;
	}

	public String getFengShuisColorConflict() {
		return fengShuisColorConflict;
	}

	public void setFengShuisColorConflict(String fengShuisColorConflict) {
		this.fengShuisColorConflict = fengShuisColorConflict;
	}

}
