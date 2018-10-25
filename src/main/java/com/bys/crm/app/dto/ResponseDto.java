package com.bys.crm.app.dto;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDto {
	
	@JsonProperty("error_code")
	private ErrorCodeEnum code;
	@JsonProperty("error_message")
	private String message;

	private Object result;
		
	public ResponseDto(Object result) {
		this.result = result;
	}
	
	public ResponseDto(Object result, String message) {
		this.result = result;
		this.message = message;
	}
	
	public ResponseDto(Object result, ErrorCodeEnum code) {
		this.result = result;
		this.code = code;
	}
	
	public ResponseDto(ErrorCodeEnum code, String message) {
		this.code = code;
		this.message = message;
	}


	public ErrorCodeEnum getCode() {
		return code;
	}
	public void setCode(ErrorCodeEnum code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
