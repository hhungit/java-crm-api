package com.bys.crm.app.event;

import org.springframework.context.ApplicationEvent;

public class ForgotPasswordEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String token;

	public ForgotPasswordEvent(Object source, String email, String token) {
		super(source);
		this.email = email;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}
	

}
