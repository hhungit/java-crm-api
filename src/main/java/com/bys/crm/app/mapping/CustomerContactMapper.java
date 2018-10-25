package com.bys.crm.app.mapping;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CustomerContactDto;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.util.ListUtil;

@Component
public class CustomerContactMapper extends BaseMapper<CustomerContactDto, ARCustomerContacts> {
	@Resource
	PasswordEncoder passwordEncoder;

	@Autowired
	private ARCustomerContactRepository customerContactRepository;

	@Override
	public CustomerContactDto buildDto(ARCustomerContacts entity) {
		CustomerContactDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public ARCustomerContacts buildEntity(CustomerContactDto dto) {
		ARCustomerContacts entity = super.buildEntity(dto);

		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(customerContactRepository).incrementAndGet());
		}

		if (dto.getEmployee() != null && dto.getEmployee().getId() == null) {
			entity.setEmployee(null);
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			entity.setBranch(null);
		}

//		if (dto.getCustomer() != null && dto.getCustomer().getId() == null) {
//			entity.setCustomer(null);
//		}

		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() == null) {
			entity.setEmployeeGroup(null);
		}

		if (dto.getProspect() != null && dto.getProspect().getId() == null) {
			entity.setProspect(null);
		}

		return entity;
	}

	public CustomerContactDto buildDto(ARCustomerContacts entity, Integer employeeId, ArrayList<Integer> groupIds) {
		CustomerContactDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null));

		return dto;
	}
}
