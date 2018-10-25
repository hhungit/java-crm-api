package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADFengShuisDirections")
public class ADFengShuisDirections extends DomainEntity<Long> {
	@Id
	@Column(name = "ADFengShuisDirectionID", columnDefinition = "int", nullable = false)
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "ADFengShuisDirectionMain", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionMain;

	@Column(name = "ADFengShuisDirectionBowLife", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionBowLife;

	@Column(name = "ADFengShuisDirectionEast", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionEast;

	@Column(name = "ADFengShuisDirectionSouthEast", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionSouthEast;

	@Column(name = "ADFengShuisDirectionNorthEast", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionNorthEast;

	@Column(name = "ADFengShuisDirectionWest", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionWest;

	@Column(name = "ADFengShuisDirectionSouthWest", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionSouthWest;

	@Column(name = "ADFengShuisDirectionNorthWest", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionNorthWest;

	@Column(name = "ADFengShuisDirectionNorth", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionNorth;

	@Column(name = "ADFengShuisDirectionSouth", columnDefinition = "nvarchar(256)")
	private String fengShuisDirectionSouth;

	public ADFengShuisDirections() {
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

	public String getFengShuisDirectionMain() {
		return fengShuisDirectionMain;
	}

	public void setFengShuisDirectionMain(String fengShuisDirectionMain) {
		this.fengShuisDirectionMain = fengShuisDirectionMain;
	}

	public String getFengShuisDirectionBowLife() {
		return fengShuisDirectionBowLife;
	}

	public void setFengShuisDirectionBowLife(String fengShuisDirectionBowLife) {
		this.fengShuisDirectionBowLife = fengShuisDirectionBowLife;
	}

	public String getFengShuisDirectionEast() {
		return fengShuisDirectionEast;
	}

	public void setFengShuisDirectionEast(String fengShuisDirectionEast) {
		this.fengShuisDirectionEast = fengShuisDirectionEast;
	}

	public String getFengShuisDirectionSouthEast() {
		return fengShuisDirectionSouthEast;
	}

	public void setFengShuisDirectionSouthEast(String fengShuisDirectionSouthEast) {
		this.fengShuisDirectionSouthEast = fengShuisDirectionSouthEast;
	}

	public String getFengShuisDirectionNorthEast() {
		return fengShuisDirectionNorthEast;
	}

	public void setFengShuisDirectionNorthEast(String fengShuisDirectionNorthEast) {
		this.fengShuisDirectionNorthEast = fengShuisDirectionNorthEast;
	}

	public String getFengShuisDirectionWest() {
		return fengShuisDirectionWest;
	}

	public void setFengShuisDirectionWest(String fengShuisDirectionWest) {
		this.fengShuisDirectionWest = fengShuisDirectionWest;
	}

	public String getFengShuisDirectionSouthWest() {
		return fengShuisDirectionSouthWest;
	}

	public void setFengShuisDirectionSouthWest(String fengShuisDirectionSouthWest) {
		this.fengShuisDirectionSouthWest = fengShuisDirectionSouthWest;
	}

	public String getFengShuisDirectionNorthWest() {
		return fengShuisDirectionNorthWest;
	}

	public void setFengShuisDirectionNorthWest(String fengShuisDirectionNorthWest) {
		this.fengShuisDirectionNorthWest = fengShuisDirectionNorthWest;
	}

	public String getFengShuisDirectionNorth() {
		return fengShuisDirectionNorth;
	}

	public void setFengShuisDirectionNorth(String fengShuisDirectionNorth) {
		this.fengShuisDirectionNorth = fengShuisDirectionNorth;
	}

	public String getFengShuisDirectionSouth() {
		return fengShuisDirectionSouth;
	}

	public void setFengShuisDirectionSouth(String fengShuisDirectionSouth) {
		this.fengShuisDirectionSouth = fengShuisDirectionSouth;
	}

}
