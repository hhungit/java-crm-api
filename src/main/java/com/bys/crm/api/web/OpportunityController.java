package com.bys.crm.api.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.OpportunityDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.OpportunityFacade;

@RestController
public class OpportunityController extends BaseController {
	@Autowired
	private OpportunityFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_OPPORTUNITY)
	public ResponseDto createOpportunity(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid OpportunityDto dto) {
		return new ResponseDto(facade.createOpportunity(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_OPPORTUNITY_BY_ID)
	public ResponseDto getOpportunityById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "opportunityId") long opportunityId) {
		return new ResponseDto(facade.getOpportunityById(employeeId, opportunityId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_OPPORTUNITY)
	public ResponseDto editOpportunity(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid OpportunityDto dto) {
		return new ResponseDto(facade.editOpportunity(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_OPPORTUNITY)
	public ResponseDto deleteOpportunity(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "opportunityId") long opportunityId) {
		return new ResponseDto(facade.deleteOpportunity(opportunityId, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_OPPORTUNITY_LIST)
	public ResponseDto deleteOpportunityList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<Long> idList) {
		return new ResponseDto(facade.deleteOpportunityList(idList, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_OPPORTUNITY)
	public ResponseDto searchOpportunity(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchOpportunity(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_OPPORTUNITY_BY_NAME)
	public ResponseDto searchOpportunityByName(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "opportunityName", required = false) String opportunityName) {
		return new ResponseDto(facade.searchOpportunityByName(employeeId, opportunityName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_OPPORTUNITY)
	public ResponseDto opportunityFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "classify", required = false) String classify,
			@RequestParam(value = "step", required = false) String step,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate) {
		return new ResponseDto(facade.opportunityFilter(employeeId, searchKey, classify, step, pageNumber, pageSize,
				sortBy, direct, fromDate, toDate));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHART_OPPORTUNITY)
	public ResponseDto getOpportunityChart(@PathVariable(value = "employeeId") Long employeeId,
			@PathVariable(value = "type") String type, @RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		return new ResponseDto(facade.getOpportunityChart(employeeId, from, to, type));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_OPPORTUNITY_BY_CUSTOMER_ID)
	public ResponseDto getOpportunitiesByCustomerId(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@PathVariable(value = "customerId") Long customerId,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(
				facade.getOpportunitiesByCustomerId(employeeId, customerId, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_OPPORTUNITY_BY_CONTACT_ID)
	public ResponseDto getOpportunitiesByContacId(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@PathVariable(value = "contactId") Long contactId,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(
				facade.getOpportunitiesByContacId(employeeId, contactId, pageNumber, pageSize, sortBy, direct));
	}
}
