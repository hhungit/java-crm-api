package com.bys.crm.app.dto;

import com.bys.crm.domain.erp.model.ADUsers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PasswordDto {
	
	private String token;
	private String username;
	
	private String oldPassword;
	
	private String newPassword;
	
	private UserDeviceDto device;
	
	@JsonIgnore
	private ADUsers user;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ADUsers getUser() {
		return user;
	}
	public void setUser(ADUsers user) {
		this.user = user;
	}
	public UserDeviceDto getDevice() {
		return device;
	}
	public void setDevice(UserDeviceDto device) {
		this.device = device;
	}
}
