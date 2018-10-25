package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.StateProvinceDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.StateProvinceMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.GECountrys;
import com.bys.crm.domain.erp.model.GEStateProvinces;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.GECountrysRepository;
import com.bys.crm.domain.erp.repository.GEStateProvincesRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class StateProvinceFacade {
	@Autowired
	private StateProvinceMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private GEStateProvincesRepository stateProvincesRepository;

	@Autowired
	private GECountrysRepository countrysRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	// Create province
	@Transactional
	public StateProvinceDto createStateProvince(StateProvinceDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation country id
		if (dto.getCountry().getId() == null) {
			throw new InvalidException("Country id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check country is exist or not
		GECountrys country = countrysRepository.findByIdAndStatus(dto.getCountry().getId(), AAStatus.Alive.name());
		if (country == null) {
			throw new InvalidException("Country is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from dto to entity
		GEStateProvinces entity = mapper.buildEntity(dto);

		// Generate new id
		entity.setId(keyGenerationService.findMaxId(stateProvincesRepository).incrementAndGet());

		// Save data into DB
		stateProvincesRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get province by id
	public StateProvinceDto getStateProvinceById(Integer employeeId, Long stateProvinceId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		GEStateProvinces stateProvince = stateProvincesRepository.findByIdAndStatus(stateProvinceId,
				AAStatus.Alive.name());

		if (stateProvince == null) {
			throw new InvalidException("Province is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(stateProvince);
	}

	// Edit province
	@Transactional
	public StateProvinceDto editStateProvince(StateProvinceDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation id
		if (dto.getId() == null) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check province is exist or not
		GEStateProvinces stateProvince = stateProvincesRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());
		if (stateProvince == null) {
			throw new InvalidException("Province is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		stateProvince.setName(dto.getName());
		stateProvince.setCode(dto.getCode());

		// Save data into DB
		stateProvincesRepository.save(stateProvince);

		return mapper.buildDto(stateProvince);
	}

	// Delete province
	@Transactional
	public String deleteStateProvince(Long stateProvinceId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check province is exist or not
		GEStateProvinces stateProvince = stateProvincesRepository.findByIdAndStatus(stateProvinceId,
				AAStatus.Alive.name());
		if (stateProvince == null) {
			throw new InvalidException("Province is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		stateProvince.setStatus(AAStatus.Delete.name());

		// Save data into DB
		stateProvincesRepository.save(stateProvince);

		return StatusMessengerEnum.Successful.name();
	}

	// Get Province list
	public List<StateProvinceDto> getStateProvinceList(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<GEStateProvinces> stateProvinceList = stateProvincesRepository
				.findByStatusOrderByNameAsc(AAStatus.Alive.name());

		if (stateProvinceList.isEmpty()) {
			throw new InvalidException("StateProvince is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDtos(stateProvinceList);
	}

}
