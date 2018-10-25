package com.bys.crm.config.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.UserDeviceDto;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.config.security.dto.DeviceUsernamePasswordAuthenticationToken;
import com.bys.crm.config.security.model.AuthenticatedUserCache;
import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.model.ADUsers;
import com.bys.crm.domain.erp.model.ADUserDevices;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.domain.erp.repository.ADUserDevicesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

public class DeviceUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceUsernamePasswordAuthenticationProvider.class);
	
	@Autowired
	private ADUsersRepository userRepository;
	
	@Autowired
	private ADUserDevicesRepository deviceRepository;
	
	@Autowired
	private GenericMapper genericMapper;
	
	@Autowired 
	KeyGenerationService keyGenerationService;
	
	@Override
	public boolean supports(Class<?> authentication) {
		return DeviceUsernamePasswordAuthenticationToken.class == authentication;
	}

	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DeviceUsernamePasswordAuthenticationToken token = (DeviceUsernamePasswordAuthenticationToken) authentication;
		Authentication authenticated = super.authenticate(token);
		
		UserDeviceDto deviceDto = token.getDevice();
		
		if (deviceDto != null) {
			AuthenticatedUserCache userCache = (AuthenticatedUserCache) this.getUserCache();
			DatabaseUserDetails userDetails = (DatabaseUserDetails) userCache.getUserFromCache(token.getUserName());
					
			userCache.removeUserFromCache(token.getUserName());			
			userCache.putUserInCache(token.getLoginName(), userDetails);
					
			ADUsers user = userDetails.getUser();
			ADUserDevices device = deviceRepository.findByDeviceKey(deviceDto.getDeviceKey());
			if (device == null) {
				LOGGER.info("There's no user linked to device {} - create new...", deviceDto.getDeviceKey());
				device = genericMapper.buildObject(deviceDto, ADUserDevices.class);
				device.setId(keyGenerationService.getADUserDeviceId());
			}
			device.setActive(true);
			device.setUser(user);
			user.setAuthenticatedDevice(device);
			userRepository.save(user);
		}
		

		return authenticated;
	}
}
