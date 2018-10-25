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
import org.springframework.web.multipart.MultipartFile;

import com.bys.crm.app.dto.ChangeProspectDto;
import com.bys.crm.app.dto.ProspectCustomerDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.ProspectCustomerFacade;

@RestController
public class ProspectCustomerController extends BaseController {
	@Autowired
	private ProspectCustomerFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_PROSPECT)
	public ResponseDto createProspectCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid ProspectCustomerDto dto) {
		return new ResponseDto(facade.createProspectCustomer(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_PROSPECT_BY_ID)
	public ResponseDto getProspectCustomerById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "prospectId") long prospectId) {
		return new ResponseDto(facade.getProspectCustomerById(employeeId, prospectId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_PROSPECT)
	public ResponseDto editProspectCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid ProspectCustomerDto dto) {
		return new ResponseDto(facade.editProspectCustomer(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_PROSPECT)
	public ResponseDto deleteProspectCustomer(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "prospectId") long prospectId) {
		return new ResponseDto(facade.deleteProspectCustomer(prospectId, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_PROSPECT_LIST)
	public ResponseDto deleteProspectCustomerList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<Long> idList) {
		return new ResponseDto(facade.deleteProspectCustomerList(idList, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.IMPORT_PROSPECTS_FROM_EXCEL)
	public ResponseDto importProspectCustomerFromExcel(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam("filePath") MultipartFile file) {
		return new ResponseDto(facade.importProspectCustomerFromExcel(employeeId, file));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CHANGE_PROSPECT_TO_CUSTOMER_CONTACT)
	public ResponseDto changeProspectToCustomerContact(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid ChangeProspectDto dto) {
		return new ResponseDto(facade.changeProspectToCustomerContact(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_PROSPECT)
	public ResponseDto searchProspect(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchProspect(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_PROSPECT_BY_NAME)
	public ResponseDto searchProspectByName(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "prospectName", required = false) String prospectName) {
		return new ResponseDto(facade.searchProspectByName(employeeId, prospectName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_PROSPECT)
	public ResponseDto prospectFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "rate", required = false) String rate,
			@RequestParam(value = "prospectSource", required = false) Long prospectSourceId,
			@RequestParam(value = "business", required = false) String business,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate) {
		return new ResponseDto(facade.prospectFilter(employeeId, searchKey, rate, prospectSourceId, business, pageNumber,
				pageSize, sortBy, direct, fromDate, toDate));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_DELETED_PROSPECT_BY_ID)
	public ResponseDto getDeletedProspectById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "prospectId") long prospectId) {
		return new ResponseDto(facade.getDeletedProspectById(employeeId, prospectId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHART_PROSPECT)
	public ResponseDto getProspectCustomerChart(@PathVariable(value = "employeeId") Long employeeId,
			@PathVariable(value = "type") String type, @RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		return new ResponseDto(facade.getProspectCustomerChart(employeeId, from, to, type));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CHANGE_PROSPECT_TO_CUSTOMER_CONTACT_ERP)
	public ResponseDto changeProspectToCustomerContactERP(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "token") String token,
			@RequestBody @Valid ChangeProspectDto dto) {
		return new ResponseDto(facade.changeProspectToCustomerContactERP(dto, employeeId, token));
	}
}
