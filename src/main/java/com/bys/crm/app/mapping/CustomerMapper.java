package com.bys.crm.app.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CustomerDto;
import com.bys.crm.app.dto.LocationDto;
import com.bys.crm.domain.erp.constant.CustomerType;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.util.ListUtil;

@Component
public class CustomerMapper extends BaseMapper<CustomerDto, ARCustomers> {
	@Resource
	PasswordEncoder passwordEncoder;

	@Autowired
	private GenericMapper mapper;

	@Autowired
	private ARCustomersRepository customersRepository;
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public CustomerDto buildDto(ARCustomers entity) {
		CustomerDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		if (entity.getLocation() != null) {
			dto.setLocation(this.mapper.buildObject(entity.getLocation(), LocationDto.class));
			if (entity.getLocation().getParent() != null) {
				dto.getLocation()
						.setParent(this.mapper.buildObject(entity.getLocation().getParent(), LocationDto.class));
			}
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());

		return dto;
	}

	@Override
	public ARCustomers buildEntity(CustomerDto dto) {
		ARCustomers entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(customersRepository).incrementAndGet());
			entity.setCustomerNumber(keyGenerationService.generateCustomerNumber());
		}

		if (dto.getEmployee() != null && dto.getEmployee().getId() == null) {
			entity.setEmployee(null);
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			entity.setBranch(null);
		}

		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() == null) {
			entity.setEmployeeGroup(null);
		}

		if (dto.getProspect() != null && dto.getProspect().getId() == null) {
			entity.setProspect(null);
		}

		if (CustomerType.Company.name().equals(dto.getCustomerType())) {
			entity.setDob(null);
		} else {
			entity.setCompanyEstablishmentDay(null);
		}

		entity.setGender(dto.getGender() == null ? null : Gender.valueOf(dto.getGender()));

		return entity;
	}
//	public CustomerDto buildDto(ARCustomers entity, Integer employeeId, ArrayList<Integer> groupIds) {
//		CustomerDto dto = super.buildDto(entity);
//		if (dto == null) {
//			return null;
//		}
//		if (entity.getLocation() != null) {
//			dto.setLocation(this.mapper.buildObject(entity.getLocation(), LocationDto.class));
//			if (entity.getLocation().getParent() != null) {
//				dto.getLocation()
//						.setParent(this.mapper.buildObject(entity.getLocation().getParent(), LocationDto.class));
//			}
//		}
//		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());
//
//		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
//				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
//				entity.getEmployee() != null ? entity.getEmployee().getId() : null ));
//
//		return dto;
//	}
	public CustomerDto buildDto(ARCustomers entity, Integer employeeId, ArrayList<Integer> groupIds) {
		CustomerDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(employeeId);
		Boolean isLeader = false;
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeader = employeeGroups.get(0).getIsLeader();
		}
		if (entity.getLocation() != null) {
			dto.setLocation(this.mapper.buildObject(entity.getLocation(), LocationDto.class));
			if (entity.getLocation().getParent() != null) {
				dto.getLocation()
						.setParent(this.mapper.buildObject(entity.getLocation().getParent(), LocationDto.class));
			}
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null, isLeader));

		return dto;
	}
}
