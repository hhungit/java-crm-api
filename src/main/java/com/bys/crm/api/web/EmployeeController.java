package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.EmployeeDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.EmployeeFacade;

@RestController
public class EmployeeController extends BaseController {
	@Autowired
	private EmployeeFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_EMPLOYEE_LIST)
	public ResponseDto getEmployeesList(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getEmployeesList(employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_EMPLOYEE_BY_ID)
	public ResponseDto getEmployeeById(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getEmployeeById(employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_EMPLOYEE)
	public ResponseDto createEmployee(@PathVariable(value = "id") long id, @RequestBody @Valid EmployeeDto dto) {
		return new ResponseDto(facade.createEmployee(dto, id));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_EMPLOYEE_BY_BRANCH)
	public ResponseDto getEmployeesListByBranch(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "branchId") long branchId) {
		return new ResponseDto(facade.getEmployeesListByBranch(employeeId, branchId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_EMPLOYEES_PAGING)
	public ResponseDto getEmployeesPaging(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getEmployeesPaging(employeeId, pageNumber, pageSize));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_EMPLOYEE)
	public ResponseDto editEmployee(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid EmployeeDto dto) {
		return new ResponseDto(facade.editEmployee(employeeId, dto));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_AVATAR_BY_ID)
	public ResponseDto getAvatarById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "id") int id) {
		return new ResponseDto(facade.getAvatarById(employeeId, id));
	}
}
