package com.bys.crm.app.mapping;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CompanySignupDto;
import com.bys.crm.domain.erp.constant.ARCustomerType;
import com.bys.crm.domain.erp.model.ARCustomers;

@Component
public class CompanyCustomerMapper extends BaseMapper<CompanySignupDto, ARCustomers>{
	
	@Resource
	PasswordEncoder passwordEncoder;
	
	@Override
	public ARCustomers buildEntity(CompanySignupDto dto) {
		ARCustomers entity = super.buildEntity(dto);
		if (entity == null) {
			return null;
		}
		entity.setCustomerType(ARCustomerType.Employer.name());
		entity.setCustomerNumber(keyGenerationService.generateCustomerNumber());
		entity.setId(keyGenerationService.getARCustomerId());
		
		
//		ADPortalUsers user = new ADPortalUsers(entity);
//		user.setUsername(dto.getEmail());
//		user.setPassword(passwordEncoder.encode(dto.getPassword()));
//		user.setId(keyGenerationService.getADPortalUserId());
		
//		entity.setUser(user);
				
		return entity;
		
	}
}
