package com.bys.crm.api.web;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.EmailMailChimpDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.email.EmailMailChimpService;
import com.ecwid.maleorang.MailchimpException;

@RestController
public class EmailChimpController extends BaseController{

	@Autowired
	private EmailMailChimpService emailService;
	
	@RequestMapping(method = RequestMethod.POST, value = RestURL.SEND_EMAIL)
	public ResponseDto createProspectCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid EmailMailChimpDto dto){
		return new ResponseDto(emailService.sendEmail(employeeId, dto));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEND_EMAIL_CAMPAIGN)
	public ResponseDto createProspectCustomer(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "campaignID") String campaignID) throws IOException, MailchimpException {
		return new ResponseDto(emailService.runSendSchedule(campaignID));
	}

}
