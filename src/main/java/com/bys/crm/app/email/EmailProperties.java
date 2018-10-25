package com.bys.crm.app.email;

import java.util.Properties;

public class EmailProperties {
	
	private String subject;
	private String emailFrom;
	private String emailTo;
	private String emailCc;
	private String templateName;
	
	private Properties properties = new Properties(); 
	
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Properties getProperties() {
		return properties;
	}
		
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailCc() {
		return emailCc;
	}
	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}
	
	public String[] getEmailFromList() {
		return new String[] {emailFrom};
	}
	
	public String[] getEmailToList() {
		return new String[] {emailTo};
	}
	
	public String[] getEmailCcList() {
		return emailCc == null?null: new String[] {emailTo};
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	

}
