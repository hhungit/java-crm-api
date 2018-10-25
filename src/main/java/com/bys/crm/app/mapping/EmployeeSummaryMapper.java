package com.bys.crm.app.mapping;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.util.StringUtil;

@Component
public class EmployeeSummaryMapper extends BaseMapper<EmployeeSummaryDto, HREmployees> {
	@Resource
	PasswordEncoder passwordEncoder;

	@Autowired
	HREmployeesRepository employeesRepository;
	
	@Autowired
	ADUsersRepository adPortalUsersRepository;
	
	@Override
	public EmployeeSummaryDto buildDto(HREmployees entity) {
		EmployeeSummaryDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		
		if (!StringUtil.isEmpty(dto.getName()) && entity.getBranch() != null) {
			dto.setName(dto.getName().concat(" - ").concat(entity.getBranch().getName()));
		}

		return dto;
	}

	@Override
	public HREmployees buildEntity(EmployeeSummaryDto dto) {
		HREmployees entity = super.buildEntity(dto);
		
		return entity;
	}
	
}
