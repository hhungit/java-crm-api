package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "HREmployeeGroups")
public class HREmployeeGroups extends DomainEntity<Integer>{
	@Id
	@Column(name = "HREmployeeGroupID", nullable = false)
	private Integer id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "FK_HREmployeeID", nullable = false, columnDefinition = "int")
	private Integer employeeId;

	@Column(name = "FK_BRBranchID", nullable = false, columnDefinition = "int")
	private Integer branchId;

	@Column(name = "IsLeader", nullable = false, columnDefinition = "bit")
	private Boolean isLeader;

	public HREmployeeGroups() {
		this.status = AAStatus.Alive.name();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

//	public Integer getGroupId() {
//		return groupId;
//	}
//
//	public void setGroupId(Integer groupId) {
//		this.groupId = groupId;
//	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

}
