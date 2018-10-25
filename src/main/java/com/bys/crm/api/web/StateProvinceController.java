package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.StateProvinceDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.StateProvinceFacade;

@RestController
public class StateProvinceController extends BaseController {
	@Autowired
	private StateProvinceFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_PROVINCE)
	public ResponseDto createStateProvince(@PathVariable(value = "employeeId") int employeeId,
										   @RequestBody @Valid StateProvinceDto dto) {
		return new ResponseDto(facade.createStateProvince(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_PROVINCE_BY_ID)
	public ResponseDto getStateProvinceById(@PathVariable(value = "employeeId") int employeeId,
											@PathVariable(value = "provinceId") long provinceId) {
		return new ResponseDto(facade.getStateProvinceById(employeeId, provinceId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_PROVINCE)
	public ResponseDto editStateProvince(@PathVariable(value = "employeeId") int employeeId,
										 @RequestBody @Valid StateProvinceDto dto) {
		return new ResponseDto(facade.editStateProvince(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_PROVINCE)
	public ResponseDto deleteStateProvince(@PathVariable(value = "employeeId") int employeeId,
										   @PathVariable(value = "provinceId") long provinceId) {
		return new ResponseDto(facade.deleteStateProvince(provinceId, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_PROVINCE_LIST)
	public ResponseDto getStateProvinceList(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getStateProvinceList(employeeId));
	}
}
