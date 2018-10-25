package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.BranchFacade;

@RestController
public class BranchController extends BaseController {
	@Autowired
	private BranchFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_BRANCH_LIST)
	public ResponseDto getBranchsList() {
		return new ResponseDto(facade.getBranchsList());
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_BRANCHS_BY_EMPLOYEE)
	public ResponseDto getBranchsByEmployee(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getBranchsByEmployee(employeeId));
	}
}
