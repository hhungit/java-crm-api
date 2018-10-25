package com.bys.crm.config.security.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.bys.crm.app.dto.UserDeviceDto;


public class DeviceUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private UserDeviceDto device;

	public DeviceUsernamePasswordAuthenticationToken(UserLoginDto dto) {
		super(dto.getUsername(), dto.getPassword());
		this.device = dto.getDevice();

	}

	public String getUserName() {
		return (String) this.getPrincipal();
	}

	public String getLoginName() {
		StringBuilder builder = new StringBuilder(getUserName());
		if (device != null) {
			builder.append("#").append(device.getDeviceKey()).append("#").append(device.getDeviceType());
		}
		return builder.toString();
	}

	public UserDeviceDto getDevice() {
		return device;
	}

}
