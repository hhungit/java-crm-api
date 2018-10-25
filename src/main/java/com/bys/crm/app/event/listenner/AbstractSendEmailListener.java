package com.bys.crm.app.event.listenner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.bys.crm.app.email.EmailProperties;
import com.bys.crm.app.email.EmailService;

public abstract class AbstractSendEmailListener<E extends ApplicationEvent> implements ApplicationListener<E > {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSendEmailListener.class);
	
	@Autowired
	private EmailService emailService;
	
	protected abstract EmailProperties buildProperties(E event);
	
	@Override
	public void onApplicationEvent(E event) {
		LOGGER.info("Listening event {} -- sending email ", event.getClass().getSimpleName());
		emailService.sendHtmlEmail(buildProperties(event));
			
	}
}
