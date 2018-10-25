package com.bys.crm.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceUsernamePasswordLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		ResponseDto dto = new ResponseDto(ErrorCodeEnum.NOT_AUTHORIZE, "Bad credentials");
		response.getWriter().write(mapper.writeValueAsString(dto));

		response.getWriter().flush();

	}
}
