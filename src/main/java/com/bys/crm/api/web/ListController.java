package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.ListFacade;

@RestController
public class ListController extends BaseController {
	@Autowired
	private ListFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COMMON_LIST)
	public ResponseDto getCommonList(@PathVariable(value = "listType") String listType) {
		return new ResponseDto(facade.getCommonList(listType));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COMMON_LIST_CUSTOMER_RESOURCE)
	public ResponseDto getCustomerResources() {
		return new ResponseDto(facade.getCustomerResources());
	}
}
