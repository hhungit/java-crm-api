package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CommonFacade;
import com.bys.crm.app.facade.LunarFacade;

@RestController
public class CommonController extends BaseController {
	@Autowired
	private CommonFacade facade;

	@Autowired
	private LunarFacade lunarFacade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_PHONE_NUMBER)
	public ResponseDto searchPhoneNumber(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "phoneNumber") String phoneNumber) {
		return new ResponseDto(facade.searchPhoneNumber(employeeId, phoneNumber));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHECT_EXIST_PHONE_NUMBER)
	public ResponseDto checkExistPhoneNumber(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "branchId") int branchId,
			@PathVariable(value = "phoneNumber") String phoneNumber) {
		return new ResponseDto(facade.isExistPhoneNumber(employeeId, branchId, phoneNumber));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_LUNAR_YEAR_FROM_SOLAR_YEAR)
	public ResponseDto checkExistPhoneNumber(@PathVariable(value = "solarYear") String solarYear) {
		return new ResponseDto(lunarFacade.getLunarYear(solarYear));
	}
}
