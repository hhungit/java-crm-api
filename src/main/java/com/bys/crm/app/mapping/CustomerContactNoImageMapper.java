package com.bys.crm.app.mapping;

import java.util.ArrayList;

import javax.annotation.Resource;

 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

 
import com.bys.crm.app.dto.CustomerContactNoImageDto;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
 
import com.bys.crm.util.ListUtil;

@Component
public class CustomerContactNoImageMapper  extends BaseMapper<CustomerContactNoImageDto, ARCustomerContacts> {
	
	@Resource
	PasswordEncoder passwordEncoder;

	public CustomerContactNoImageDto buildDto(ARCustomerContacts entity, Integer employeeId, ArrayList<Integer> groupIds) {
		CustomerContactNoImageDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null));

		return dto;
	}
}
