package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.BranchsDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.BRBranchsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.EmployeeService;

@Service
public class BranchFacade {
	@Autowired
	private GenericMapper mapper;

	@Autowired
	private BRBranchsRepository branchsRepository;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private EmployeeService employeeService;
	
	// Get branch list
	public List<BranchsDto> getBranchsList(){
		List<BRBranchs> branchsList = branchsRepository.findByStatus(AAStatus.Alive.name());

		if (branchsList.isEmpty()) {
			throw new InvalidException("Branch is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildObjects(branchsList, BranchsDto.class);
	}

	// Get branchs by employee
	public List<BranchsDto> getBranchsByEmployee(Integer employeeId){
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}
		
		List<Long> ids = employeeService.getBranchIDs(employeeId);
		
		List<BRBranchs> branchsList = branchsRepository.findByStatusAndIdIn(AAStatus.ALive.name(), ids);

		return mapper.buildObjects(branchsList, BranchsDto.class);
	}

}
