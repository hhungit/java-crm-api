package com.bys.crm.app.mapping;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.EmployeeDto;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ADUsers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Component
public class EmployeeMapper extends BaseMapper<EmployeeDto, HREmployees> {
	@Resource
	PasswordEncoder passwordEncoder;

	@Autowired
	HREmployeesRepository employeesRepository;
	
	@Autowired
	ADUsersRepository adPortalUsersRepository;
	
	@Override
	public EmployeeDto buildDto(HREmployees entity) {
		EmployeeDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());
		return dto;
	}

	@Override
	public HREmployees buildEntity(EmployeeDto dto) {
		HREmployees entity = super.buildEntity(dto);
		entity.setGender(dto.getGender() == null ? null : Gender.valueOf(dto.getGender()));
		if (dto.getId() == null) {
			entity.setId(Long.valueOf(keyGenerationService.findMaxId(employeesRepository).incrementAndGet()).intValue());
			entity.setEmployeeNumber(keyGenerationService.generateEmployeeNumber());
			if (dto.getEmail() != null) {
				entity.setPassword(passwordEncoder.encode("bys123@#$"));
			}
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			entity.setBranch(null);
		}

		ADUsers user = new ADUsers();
		user.setId(keyGenerationService.findMaxId(adPortalUsersRepository).incrementAndGet());
		user.setUsername(dto.getEmail());
		user.setPassword(passwordEncoder.encode("bys123@#$"));
//		user.setType(ARCustomerType.Employer.name());
//		user.setEmployeeId(Long.valueOf(entity.getId()));
		user.setEmployee(entity);
		
		entity.setUser(user);
		
		return entity;
	}
	
}
