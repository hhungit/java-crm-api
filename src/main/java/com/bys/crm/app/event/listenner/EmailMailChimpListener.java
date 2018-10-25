package com.bys.crm.app.event.listenner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bys.crm.app.email.EmailMailChimpService;
import com.bys.crm.app.event.EmailMailChimpEvent;

@Component
public class EmailMailChimpListener implements ApplicationListener<EmailMailChimpEvent> {

	@Autowired
	private EmailMailChimpService emailMailChimpService;

	@Override
	public void onApplicationEvent(EmailMailChimpEvent event) {
		this.emailMailChimpService.runSend(event.getCampaignInfo());
	}
}
