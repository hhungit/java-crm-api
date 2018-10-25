package com.bys.crm.app.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.service.EmployeeService;

@Component
public class EmployeeValidator {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * Validation employeeId
	 * 
	 * @param employeeId
	 * @return HREmployees
	 */
	public HREmployees validateEmployeeId(Integer employeeId) {
		HREmployees employee = employeeService.getEmployee(employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		return employee;
	}
}
