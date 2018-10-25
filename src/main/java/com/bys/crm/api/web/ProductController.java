package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.ProductFacade;

@RestController
public class ProductController extends BaseController{

	@Autowired
	private ProductFacade productFacade;
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_PRODUCTS)
	public ResponseDto getProducts(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(productFacade.getProducts(employeeId));
	}
}
