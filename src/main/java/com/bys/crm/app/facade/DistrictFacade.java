package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.DistrictDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.DistrictMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.GEDistricts;
import com.bys.crm.domain.erp.model.GEStateProvinces;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.GEDistrictsRepository;
import com.bys.crm.domain.erp.repository.GEStateProvincesRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class DistrictFacade {
	@Autowired
	private DistrictMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private GEDistrictsRepository districtsRepository;

	@Autowired
	private GEStateProvincesRepository provincesRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	// Create district
	@Transactional
	public DistrictDto createDistrict(DistrictDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation province id
		if (dto.getProvince().getId() == null) {
			throw new InvalidException("Province id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check district is exist or not
		GEStateProvinces province = provincesRepository.findByIdAndStatus(dto.getProvince().getId(),
				AAStatus.Alive.name());
		if (province == null) {
			throw new InvalidException("Province is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from dto to entity
		GEDistricts entity = mapper.buildEntity(dto);

		// Generate new id
		entity.setId(keyGenerationService.findMaxId(districtsRepository).incrementAndGet());

		// Save data into DB
		districtsRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get district by id
	public DistrictDto getDistrictById(Integer employeeId, Long districtId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		GEDistricts district = districtsRepository.findByIdAndStatus(districtId, AAStatus.Alive.name());

		if (district == null) {
			throw new InvalidException("District is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(district);
	}

	// Edit district
	@Transactional
	public DistrictDto editDistrict(DistrictDto dto, Integer employeeId) {
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

		// Check district is exist or not
		GEDistricts district = districtsRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());
		if (district == null) {
			throw new InvalidException("District is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		district.setName(dto.getName());
		district.setCode(dto.getCode());

		// Save data into DB
		districtsRepository.save(district);

		return mapper.buildDto(district);
	}

	// Delete district
	@Transactional
	public String deleteDistrict(Long districtId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check district is exist or not
		GEDistricts district = districtsRepository.findByIdAndStatus(districtId, AAStatus.Alive.name());
		if (district == null) {
			throw new InvalidException("District is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		district.setStatus(AAStatus.Delete.name());

		// Save data into DB
		districtsRepository.save(district);

		return StatusMessengerEnum.Successful.name();
	}

	// Get District list
	public List<DistrictDto> getDistrictList(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<GEDistricts> districtList = districtsRepository.findByStatusOrderByNameAsc(AAStatus.Alive.name());

		if (districtList.isEmpty()) {
			throw new InvalidException("District is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDtos(districtList);
	}

}
