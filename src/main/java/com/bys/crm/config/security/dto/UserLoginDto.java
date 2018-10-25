package com.bys.crm.config.security.dto;

import com.bys.crm.app.dto.UserDeviceDto;
import com.bys.crm.util.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserLoginDto {
	private String username;
	private String password;
	
	private UserDeviceDto device;
	
	public UserLoginDto() {
		
	}
	
	public UserLoginDto(String userName, String password, UserDeviceDto device) {
		this.username = userName;
		this.password = password;
		this.device = device;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public UserDeviceDto getDevice() {
		return device;
	}

	public void setDevice(UserDeviceDto device) {
		this.device = device;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return device == null ? username
				: SecurityUtil.getLoginName(username, device.getDeviceKey(), device.getDeviceType());
	}

}
