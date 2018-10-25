package com.bys.crm.app.mapping;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.UserSignupDto;
import com.bys.crm.domain.erp.constant.ARCustomerType;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.repository.GELocationsRepository;


@Component
public class IndividualCustomerMapper extends BaseMapper<UserSignupDto, ARCustomers>{
		
	@Resource
	PasswordEncoder passwordEncoder;
	
	@Autowired
	GELocationsRepository geLocationsRepository;
	
	@Override
	public ARCustomers buildEntity(UserSignupDto dto) {
		ARCustomers entity = super.buildEntity(dto);
		if (entity == null) {
			return entity;
		}
		entity.setCustomerType(ARCustomerType.Employer.name());
		entity.setCustomerNumber(keyGenerationService.generateCustomerNumber());
		entity.setId(keyGenerationService.getARCustomerId());
		
//		ADPortalUsers user = new ADPortalUsers(entity);
//		user.setUsername(dto.getEmail());
//		user.setPassword(passwordEncoder.encode(dto.getPassword()));
//		user.setId(keyGenerationService.getADPortalUserId());
//		
//		entity.setUser(user);
						
		return entity;
		
	}

}
