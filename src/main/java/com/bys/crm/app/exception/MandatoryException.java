package com.bys.crm.app.exception;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;

public class MandatoryException extends RuntimeException {
	private static final long serialVersionUID = 7271159904629535750L;
	private ErrorCodeEnum errorCode;
	
	public MandatoryException(String message) {
		super(message);
		this.errorCode = ErrorCodeEnum.INVALID_REQUEST;
	}
	
	public MandatoryException(String message, ErrorCodeEnum errorCode) {
		this(message);
		this.errorCode = errorCode;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	
	
}
