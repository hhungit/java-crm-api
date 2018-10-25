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

import com.bys.crm.app.dto.CustomerDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CustomerFacade;

@RestController
public class CustomerController extends BaseController {
	@Autowired
	private CustomerFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_CUSTOMER)
	public ResponseDto createCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CustomerDto dto) {
		return new ResponseDto(facade.createCustomer(dto, employeeId, RestURL.CREATE_CUSTOMER));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CUSTOMER_BY_ID)
	public ResponseDto getCustomerById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId) {
		return new ResponseDto(facade.getCustomerById(employeeId, customerId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_CUSTOMER)
	public ResponseDto editCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CustomerDto dto) {
		return new ResponseDto(facade.editCustomer(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CUSTOMER)
	public ResponseDto deleteCustomer(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId) {
		return new ResponseDto(facade.deleteCustomer(customerId, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CUSTOMER_LIST)
	public ResponseDto deleteCustomerList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<Long> idList) {
		return new ResponseDto(facade.deleteCustomerList(idList, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.IMPORT_CUSTOMERS_FROM_EXCEL)
	public ResponseDto importCustomerFromExcel(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam("filePath") MultipartFile file) {
		return new ResponseDto(facade.importCustomerFromExcel(employeeId, file));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CUSTOMER)
	public ResponseDto searchCustomer(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchCustomer(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CUSTOMER_BY_NAME)
	public ResponseDto searchCustomerByName(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "customerName", required = false) String customerName) {
		return new ResponseDto(facade.searchCustomerByName(employeeId, customerName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_CUSTOMER)
	public ResponseDto customerFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "business", required = false) String business,
			@RequestParam(value = "group", required = false) String group,
			@RequestParam(value = "evaluate", required = false) Long evaluate,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate) {
		return new ResponseDto(facade.customerFilter(employeeId, searchKey, business, group, evaluate, pageNumber,
				pageSize, sortBy, direct, fromDate, toDate));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHART_CUSTOMER)
	public ResponseDto getCustomerChart(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "type") String type, @RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		return new ResponseDto(facade.getCustomerChart(employeeId, from, to, type));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHECK_CUSTOMER_NAME_EXIST)
	public ResponseDto checkCustomerNameExist(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "name", required = true) String customerName) {
		return new ResponseDto(facade.checkCustomerNameExist(employeeId, customerName));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CUSTOMER_BY_CONTACT_ID)
	public ResponseDto getCustomerByContactId(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "contactId") long contactId) {
		return new ResponseDto(facade.getCustomerByContactId(employeeId, contactId));
	}
}
