package com.bys.crm.app.dto;

public class IsLeaderDto {

	private Integer branchId;

	private Boolean isLeader;

	public IsLeaderDto(Integer branchId, Boolean isLeader) {
		super();
		this.branchId = branchId;
		this.isLeader = isLeader;
	}

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
