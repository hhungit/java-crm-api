package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.OpportunityContactDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.OpportunityContactFacade;

@RestController
public class OpportunityContactController extends BaseController {
	@Autowired
	private OpportunityContactFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_OPPORTUNITY_CONTACT)
	public ResponseDto createOpportunityContact(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid OpportunityContactDto dto) {
		return new ResponseDto(facade.createOpportunityContact(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_OPPORTUNITYS_CONTACT_BY_OPPORTUNITY_ID)
	public ResponseDto getOpportunityContactByOpportunityId(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "opportunityId") long opportunityId) {
		return new ResponseDto(facade.getOpportunityContactByOpportunityId(employeeId, opportunityId));
	}

}
