package com.bys.crm.app.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.bys.crm.domain.erp.model.ARNotifications;


public class NotificationEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private List<ARNotifications> notifications;
	
	public NotificationEvent(Object source, List<ARNotifications> notifications) {
		super(source);
		this.setNotifications(notifications);
	}

	public List<ARNotifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<ARNotifications> notifications) {
		this.notifications = notifications;
	}

}