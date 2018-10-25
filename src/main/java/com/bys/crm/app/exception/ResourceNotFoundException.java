package com.bys.crm.app.exception;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7271159904629535750L;
	private ErrorCodeEnum errorCode;
	
	public ResourceNotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCodeEnum.RESOURCE_NOT_FOUND;
	}
	
	public ResourceNotFoundException(String message, ErrorCodeEnum errorCode) {
		this(message);
		this.errorCode = errorCode;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	
	
}
