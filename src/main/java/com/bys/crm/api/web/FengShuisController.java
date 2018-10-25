package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.FengShuisFacade;

@RestController
public class FengShuisController extends BaseController {
	@Autowired
	private FengShuisFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_FENG_SHUIS_INFOR)
	public ResponseDto getFengShuisInfor(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "lunarBirthday", required = true) String lunarBirthday,
			@RequestParam(value = "gender", required = true) String gender) {
		return new ResponseDto(facade.getFengShuisInfor(employeeId, lunarBirthday, gender));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_LUNAR_YEARS_LIST)
	public ResponseDto getLunarYears(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getLunarYears(employeeId));
	}
}
