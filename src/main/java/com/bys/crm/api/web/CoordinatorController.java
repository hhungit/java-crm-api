package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CoordinatorFacade;

@RestController
public class CoordinatorController extends BaseController{

	@Autowired
	private CoordinatorFacade facade;
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COORDINATOR_BY_NO)
	public ResponseDto getCoordinatorByNo(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "coordinatorNo", required = true) String coordinatorNo) {
		return new ResponseDto(facade.getCoordinatorByNo(employeeId, coordinatorNo));
	}
}
