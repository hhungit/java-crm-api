package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.DistrictDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.DistrictFacade;

@RestController
public class DistrictController extends BaseController {
	@Autowired
	private DistrictFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_DISTRICT)
	public ResponseDto createDistrict(@PathVariable(value = "employeeId") int employeeId,
									  @RequestBody @Valid DistrictDto dto) {
		return new ResponseDto(facade.createDistrict(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_DISTRICT_BY_ID)
	public ResponseDto getDistrictById(@PathVariable(value = "employeeId") int employeeId,
									   @PathVariable(value = "districtId") long districtId) {
		return new ResponseDto(facade.getDistrictById(employeeId, districtId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_DISTRICT)
	public ResponseDto editDistrict(@PathVariable(value = "employeeId") int employeeId,
								    @RequestBody @Valid DistrictDto dto) {
		return new ResponseDto(facade.editDistrict(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_DISTRICT)
	public ResponseDto deleteDistrict(@PathVariable(value = "employeeId") int employeeId,
									  @PathVariable(value = "districtId") long districtId) {
		return new ResponseDto(facade.deleteDistrict(districtId, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_DISTRICT_LIST)
	public ResponseDto getDistrictList(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getDistrictList(employeeId));
	}

}
