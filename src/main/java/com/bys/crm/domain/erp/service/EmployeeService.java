package com.bys.crm.domain.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.HREmployeeGroupsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private HREmployeeGroupsRepository employeeGroupsRepository;
	
	public HREmployees getEmployee(Integer employeeId) {
		return employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
	}

	public List<HREmployeeGroups> getEmployeeGroups(Integer employeeId) {
		return employeeGroupsRepository.findByStatusAndEmployeeId(AAStatus.Alive.name(), employeeId);
	}
	
	public List<Long> getBranchIDs(Integer employeeId){
		return employeeGroupsRepository.findBranchIDByStatusAndEmployeeId(AAStatus.Alive.name(), employeeId);
	}
}
