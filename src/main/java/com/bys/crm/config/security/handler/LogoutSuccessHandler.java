package com.bys.crm.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.Assert;

import com.bys.crm.config.security.jwt.JWTService;
import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.model.ADUserDevices;
import com.bys.crm.domain.erp.repository.ADUserDevicesRepository;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements InitializingBean {

	private UserCache userCache;
	private JWTService jwtService;
	
	@Autowired
	private ADUserDevicesRepository deviceRepository;

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setJwtService(JWTService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null) {
			String username = jwtService.decode(token);
			DatabaseUserDetails userDetails = (DatabaseUserDetails) userCache.getUserFromCache(username);
			ADUserDevices device = userDetails.getUser().getAuthenticatedDevice();
			
			if (device != null) {
				device.setActive(false);
				deviceRepository.save(device);
				userDetails.getUser().setAuthenticatedDevice(null);
			}
			
			
			userCache.removeUserFromCache(username);
			
		}

		response.setStatus(HttpStatus.OK.value());
		response.getWriter().flush();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(userCache, "userCache must be set");
		Assert.notNull(jwtService, "jwtService must be set");

	}
}
