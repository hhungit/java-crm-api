package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.CoordinatorDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.ARCoordinators;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.ARCoordinatorRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class CoordinatorFacade {

	@Autowired
	private ARCoordinatorRepository repository;
	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private GenericMapper mapper;

	public CoordinatorDto getCoordinatorByNo(Integer employeeId, String coodinatorNo) {
		//System.out.println("getCoordinatorByNo "+coodinatorNo);
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<ARCoordinators> entities = repository.findByStatusAndCoordinatorNo(AAStatus.Alive.name(), coodinatorNo);

		if(entities != null && entities.size()>0) 
			return mapper.buildObject(entities.get(0), CoordinatorDto.class);
		else
			return null;
	}
}
