package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.CountryDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.CountryMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.GECountrys;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.GECountrysRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class CountryFacade {
	@Autowired
	private CountryMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private GECountrysRepository countrysRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	// Create country
	@Transactional
	public CountryDto createCountry(CountryDto dto, Integer employeeId) {
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
		GECountrys entity = mapper.buildEntity(dto);

		// Generate new id
		entity.setId(keyGenerationService.findMaxId(countrysRepository).incrementAndGet());

		// Save data into DB
		countrysRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get country by id
	public CountryDto getCountryById(Integer employeeId, Long countryId) {
		// Validation parameter
		if (countryId == null) {
			throw new InvalidException("Country Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		GECountrys country = countrysRepository.findByIdAndStatus(countryId, AAStatus.Alive.name());

		if (country == null) {
			throw new InvalidException("Country is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(country);
	}

	// Edit country
	@Transactional
	public CountryDto editCountry(CountryDto dto, Integer employeeId) {
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

		// Check country is exist or not
		GECountrys country = countrysRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());
		if (country == null) {
			throw new InvalidException("Country is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		country.setName(dto.getName());
		country.setCode(dto.getCode());

		// Save data into DB
		countrysRepository.save(country);

		return mapper.buildDto(country);
	}

	// Delete country
	@Transactional
	public String deleteCountry(Long countryId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation id
		if (countryId == null) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check country is exist or not
		GECountrys country = countrysRepository.findByIdAndStatus(countryId, AAStatus.Alive.name());
		if (country == null) {
			throw new InvalidException("Country is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		country.setStatus(AAStatus.Delete.name());

		// Save data into DB
		countrysRepository.save(country);

		return StatusMessengerEnum.Successful.name();
	}

	// Get country list
	public List<CountryDto> getCountryList(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<GECountrys> countryList = countrysRepository.findByStatusOrderByNameAsc(AAStatus.Alive.name());

		if (countryList.isEmpty()) {
			throw new InvalidException("Country is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDtos(countryList);
	}

}
