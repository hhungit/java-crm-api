package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADFengShuisGenarals")
public class ADFengShuisGenarals extends DomainEntity<Long> {
	@Id
	@Column(name = "ADFengShuisGenaralID", columnDefinition = "int", nullable = false)
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "ADFengShuisGenaralBirthdate", columnDefinition = "nvarchar(256)")
	private String fengShuisGenaralBirthdate;

	@Column(name = "ADFengShuisGenaralFiveBasicElements", columnDefinition = "nvarchar(256)")
	private String fengShuisGenaralFiveBasicElements;

	@Column(name = "ADFengShuisGenaralDescription", columnDefinition = "nvarchar(256)")
	private String fengShuisGenaralDescription;

	@Column(name = "ADFengShuisGenaralMale", columnDefinition = "nvarchar(256)")
	private String fengShuisGenaralMale;

	@Column(name = "ADFengShuisGenaralFemale", columnDefinition = "nvarchar(256)")
	private String fengShuisGenaralFemale;

	public ADFengShuisGenarals() {
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

	public String getFengShuisGenaralBirthdate() {
		return fengShuisGenaralBirthdate;
	}

	public void setFengShuisGenaralBirthdate(String fengShuisGenaralBirthdate) {
		this.fengShuisGenaralBirthdate = fengShuisGenaralBirthdate;
	}

	public String getFengShuisGenaralFiveBasicElements() {
		return fengShuisGenaralFiveBasicElements;
	}

	public void setFengShuisGenaralFiveBasicElements(String fengShuisGenaralFiveBasicElements) {
		this.fengShuisGenaralFiveBasicElements = fengShuisGenaralFiveBasicElements;
	}

	public String getFengShuisGenaralDescription() {
		return fengShuisGenaralDescription;
	}

	public void setFengShuisGenaralDescription(String fengShuisGenaralDescription) {
		this.fengShuisGenaralDescription = fengShuisGenaralDescription;
	}

	public String getFengShuisGenaralMale() {
		return fengShuisGenaralMale;
	}

	public void setFengShuisGenaralMale(String fengShuisGenaralMale) {
		this.fengShuisGenaralMale = fengShuisGenaralMale;
	}

	public String getFengShuisGenaralFemale() {
		return fengShuisGenaralFemale;
	}

	public void setFengShuisGenaralFemale(String fengShuisGenaralFemale) {
		this.fengShuisGenaralFemale = fengShuisGenaralFemale;
	}

}
