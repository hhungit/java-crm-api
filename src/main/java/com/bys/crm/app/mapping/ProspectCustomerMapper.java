package com.bys.crm.app.mapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ProspectCustomerDto;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.repository.ARProspectCustomersRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.util.ListUtil;

@Component
public class ProspectCustomerMapper extends BaseMapper<ProspectCustomerDto, ARProspectCustomers> {
	@Autowired
	private ARProspectCustomersRepository prospectCustomersRepository;

	@Autowired
	private EmployeeService employeeService;
	@Override
	public ProspectCustomerDto buildDto(ARProspectCustomers entity) {
		ProspectCustomerDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());

		return dto;
	}

	@Override
	public ARProspectCustomers buildEntity(ProspectCustomerDto dto) {
		ARProspectCustomers entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(prospectCustomersRepository).incrementAndGet());
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

		if (dto.getCustomerResource() != null && dto.getCustomerResource().getId() == null) {
			entity.setCustomerResource(null);
		}
		entity.setGender(dto.getGender() == null ? null : Gender.valueOf(dto.getGender()));

		return entity;
	}

//	public ProspectCustomerDto buildDto(ARProspectCustomers entity, Integer employeeId, ArrayList<Integer> groupIds) {
//		ProspectCustomerDto dto = super.buildDto(entity);
//		if (dto == null) {
//			return null;
//		}
//
//		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());
//
//		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
//				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
//				entity.getEmployee() != null ? entity.getEmployee().getId() : null));
//
//		return dto;
//	}
	public ProspectCustomerDto buildDto(ARProspectCustomers entity, Integer employeeId, ArrayList<Integer> groupIds ) {
		ProspectCustomerDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(employeeId);
		Boolean isLeader = false;
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeader = employeeGroups.get(0).getIsLeader();
		}
		dto.setGender(entity.getGender() == null ? null : entity.getGender().name());

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null,isLeader));

		return dto;
	}
}
