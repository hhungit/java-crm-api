package com.bys.crm.app.dto;

import java.util.List;

public class EmailMailChimpDto {
	
	private String title;
	private String sender;
	private String senderId;
	private String content;
	private String subject;
	private String from;
	private List<RecipientsInfoDto> recipients;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<RecipientsInfoDto> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<RecipientsInfoDto> recipients) {
		this.recipients = recipients;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}	
}
