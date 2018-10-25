package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.GroupDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.GroupFacade;

@RestController
public class GroupController extends BaseController {
	@Autowired
	private GroupFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_GROUP)
	public ResponseDto createGroup(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid GroupDto dto) {
		return new ResponseDto(facade.createGroup(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_GROUP_BY_ID)
	public ResponseDto getGroupById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "groupId") int groupId) {
		return new ResponseDto(facade.getGroupById(employeeId, groupId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_GROUP_LIST)
	public ResponseDto getGroupList(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getGroupList(employeeId));
	}
//	@RequestMapping(method = RequestMethod.POST, value = RestURL.ADD_EMPLOYEE_TO_GROUP)
//	public ResponseDto addEmployeeToGroup(@PathVariable(value = "employeeId") int employeeId,
//			@PathVariable(value = "empId") long empId,
//			@PathVariable(value = "groupId") long groupId) {
//		return new ResponseDto(facade.addEmployeeToGroup(employeeId, empId, groupId));
//	}
}
