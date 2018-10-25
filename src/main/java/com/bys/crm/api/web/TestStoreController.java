package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.TestStoreProcedureFacade;

@RestController
public class TestStoreController extends BaseController{

	@Autowired
	private TestStoreProcedureFacade facade;
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.TEST_CALL_STORE)
	public ResponseDto getListTestStore() {
		return new ResponseDto(facade.getListTestStore());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.TEST_CALL_STORE_CUSTOMER)
	public ResponseDto testCountCustomer() {
		return new ResponseDto(facade.testCountCustomer());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.TEST_CALL_STORE_CUSTOMER_LIST)
	public ResponseDto testCountCustomerList() {
		return new ResponseDto(facade.testCountCustomerList());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.TEST_CALL_STORE_INIT_BALANCE)
	public ResponseDto getInitBalance() {
		return new ResponseDto(facade.getInitBalance());
	}
}
