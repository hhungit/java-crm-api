package com.bys.crm.app.event.listenner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bys.crm.app.event.NotificationEvent;
import com.bys.crm.domain.erp.service.ARNotificationService;

@Component
public class NotificationEventListener implements ApplicationListener<NotificationEvent> {

	@Autowired
	private ARNotificationService notificationsService;

	@Override
	public void onApplicationEvent(NotificationEvent event) {
		this.notificationsService.save(event.getNotifications());
	}
}