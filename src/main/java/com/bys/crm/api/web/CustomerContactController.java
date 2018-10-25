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

import com.bys.crm.app.dto.CustomerContactDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CustomerContactFacade;

@RestController
public class CustomerContactController extends BaseController {
	@Autowired
	private CustomerContactFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_CONTACT)
	public ResponseDto createCustomerContact(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CustomerContactDto dto) {
		return new ResponseDto(facade.createCustomerContact(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CONTACT_BY_ID)
	public ResponseDto getCustomerContactById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "contactId") long contactId) {
		return new ResponseDto(facade.getCustomerContactById(employeeId, contactId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_CONTACT)
	public ResponseDto editCustomerContact(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CustomerContactDto dto) {
		return new ResponseDto(facade.editCustomerContact(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CONTACT)
	public ResponseDto deleteCustomerContact(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "contactId") long contactId) {
		return new ResponseDto(facade.deleteCustomerContact(contactId, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CONTACT_LIST)
	public ResponseDto deleteCustomerContactList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<Long> idList) {
		return new ResponseDto(facade.deleteCustomerContactList(idList, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CONTACT)
	public ResponseDto searchContact(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchContact(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.IMPORT_CONTACTS_FROM_EXCEL)
	public ResponseDto importContactCustomerFromExcel(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam("filePath") MultipartFile file) {
		return new ResponseDto(facade.importContactCustomerFromExcel(employeeId, file));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CONTACT_BY_NAME)
	public ResponseDto searchContactByName(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "contactName", required = false) String contactName) {
		return new ResponseDto(facade.searchContactByName(employeeId, contactName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_CONTACT)
	public ResponseDto contactFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "prospectSource", required = false) Long potentialSourceId,
			@RequestParam(value = "jobTitle", required = false) String jobTitle,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate) {
		return new ResponseDto(facade.contactFilter(employeeId, searchKey, potentialSourceId, jobTitle, pageNumber,
				pageSize, sortBy, direct, fromDate, toDate));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHART_CONTACT)
	public ResponseDto getCustomerContactChart(@PathVariable(value = "employeeId") Long employeeId,
			@PathVariable(value = "type") String type, @RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		return new ResponseDto(facade.getCustomerContactChart(employeeId, from, to, type));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHECK_CONTACT_NAME_EXIST)
	public ResponseDto checkContactNameExist(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "name", required = true) String contactName) {
		return new ResponseDto(facade.checkContactNameExist(employeeId, contactName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CONTACT_BY_CUSTOMER_ID)
	public ResponseDto getContactByCustomerId(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId) {
		return new ResponseDto(facade.getContactByCustomerId(employeeId, customerId));
	}
}
