package com.bys.crm.app.event.listenner;

import org.springframework.stereotype.Component;

import com.bys.crm.app.email.EmailProperties;
import com.bys.crm.app.email.properties.EmailForgotPasswordProperties;
import com.bys.crm.app.email.properties.GenericEmailKey;
import com.bys.crm.app.event.ForgotPasswordEvent;


@Component
public class FortgotPasswordEnventListener extends AbstractSendEmailListener<ForgotPasswordEvent> {

	@Override
	protected EmailProperties buildProperties(ForgotPasswordEvent event) {
		EmailProperties emailProperties = new EmailProperties();
		emailProperties.setSubject(EmailForgotPasswordProperties.SUBJECT);
		emailProperties.setEmailFrom(GenericEmailKey.EMAIL_FROM);
		emailProperties.setEmailTo(event.getEmail());
		emailProperties.setTemplateName(EmailForgotPasswordProperties.TEMPLATE_NAME);

		emailProperties.setProperty(EmailForgotPasswordProperties.ACTIVE_CODE, event.getToken());
		
		return emailProperties;
	}
	
}
