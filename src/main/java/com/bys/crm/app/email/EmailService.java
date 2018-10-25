package com.bys.crm.app.email;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bys.crm.util.FileUtil;


@Service
public class EmailService {

	private static final String EMAIL_TEMPLATE_FOLDER = "email-templates/";

	@Autowired
	private JavaMailSender mailSender;

	public void sendHtmlEmail(EmailProperties emailProperties)  {
		try {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(emailProperties.getEmailToList());
		helper.setReplyTo(emailProperties.getEmailFrom());
		helper.setFrom(emailProperties.getEmailFrom());
		helper.setSubject(emailProperties.getSubject());
		helper.setText(buildContent(emailProperties), true);
		if (emailProperties.getEmailCc() != null) {
			helper.setCc(emailProperties.getEmailCcList());
		}
		mailSender.send(message);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String buildContent(EmailProperties emailProperties) throws Exception {
		InputStream inputStream =  FileUtil.getInputStream(EMAIL_TEMPLATE_FOLDER + emailProperties.getTemplateName());
		
		Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
		String templateContent = scanner.useDelimiter("\\A").next();
		scanner.close();
		

		Properties properties = emailProperties.getProperties();
		if (properties == null) {
			return templateContent;
		}
		for (String key : properties.stringPropertyNames()) {
			templateContent = templateContent.replace("{{" + key + "}}", properties.getProperty(key));
		}

		return templateContent;

	}

}
