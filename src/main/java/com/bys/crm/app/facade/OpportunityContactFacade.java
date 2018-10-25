package com.bys.crm.app.facade;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.OpportunityContactDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.AROpportunityContacts;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.AROpportunityContactsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class OpportunityContactFacade {
	@Autowired
	private GenericMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private AROpportunityContactsRepository repository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	// Create opportunity contact
	@Transactional
	public OpportunityContactDto createOpportunityContact(OpportunityContactDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from dto to entity
		AROpportunityContacts entity = mapper.buildObject(dto, AROpportunityContacts.class);

		entity.setId(keyGenerationService.findMaxId(repository).incrementAndGet());
		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());

		// Save data into DB
		repository.save(entity);

		return mapper.buildObject(entity, OpportunityContactDto.class);
	}

	// Get opportunity contact by id
	public List<OpportunityContactDto> getOpportunityContactByOpportunityId(Integer employeeId, Long opportunityId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<AROpportunityContacts> opportunities = repository.findByOpportunityIdAndStatusOrderByIdDesc(opportunityId,
				AAStatus.Alive.name());

		if (opportunities == null) {
			throw new InvalidException("Opportunity is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildObjects(opportunities, OpportunityContactDto.class);
	}

}
