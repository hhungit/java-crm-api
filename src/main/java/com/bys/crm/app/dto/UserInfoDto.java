package com.bys.crm.app.dto;

import java.util.List;

import com.bys.crm.domain.erp.model.ADUsers;

public class UserInfoDto {
	private String token;
	private Long userId;	
	private String userName;
	private String type;
	private Long objectId;
//	private List<PrivilegeDetailDto> privilegeDetails;
	private List<IsLeaderDto> isLeaders;

	public UserInfoDto() {

	}

	public UserInfoDto(String token, ADUsers user) {
		this.token = token;
		this.userName = user.getUsername();
		this.userId = user.getId();
//		this.type = user.getType();
		this.objectId = Long.valueOf(user.getEmployee().getId());
	}

	public UserInfoDto(String token, ADUsers user, List<IsLeaderDto> isLeaders) {
		this.token = token;
		this.userName = user.getUsername();
		this.userId = user.getId();
//		this.type = user.getType();
		this.objectId = Long.valueOf(user.getEmployee().getId());
		this.isLeaders = isLeaders;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public List<IsLeaderDto> getIsLeaders() {
		return isLeaders;
	}

	public void setIsLeaders(List<IsLeaderDto> isLeaders) {
		this.isLeaders = isLeaders;
	}

}