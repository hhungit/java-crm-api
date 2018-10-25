package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.AnonymousFacade;

@RestController
public class AnonymousController extends BaseController {
	@Autowired
	private AnonymousFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CUSTOMER_INFOR)
	public ResponseDto getProductPrice(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId) {
		return new ResponseDto(facade.getCustomerInfor(employeeId, customerId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_INVOICES)
	public ResponseDto getInvoices(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId, @PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "productNo", required = false) String productNo,
			@RequestParam(value = "invoiceStatus", required = false) String invoiceStatus,
			@RequestParam(value = "invoiceNo", required = false) String invoiceNo) {
		return new ResponseDto(facade.getInvoices(employeeId, customerId, pageNumber, pageSize, productNo, invoiceStatus, invoiceNo));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_DEBT_DETAILS)
	public ResponseDto getDebtDetails(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "customerId") long customerId,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getDebtDetails(employeeId, customerId, fromDate, toDate, pageNumber, pageSize));
	}
}
