package com.bys.crm.config.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.config.security.dto.DeviceUsernamePasswordAuthenticationToken;
import com.bys.crm.config.security.dto.UserLoginDto;
import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.constant.ARCustomerType;
import com.bys.crm.util.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private ObjectMapper mapper = new ObjectMapper();

	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	private AuthenticationException failed;

	private HttpServletResponse response;

	protected DeviceUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		UserLoginDto userLoginDto = mapper.readValue(WebUtil.readRequestBody(request), UserLoginDto.class);
		this.response = response;
		return authenticate(request, userLoginDto);
	}

	public Authentication authenticate(HttpServletRequest httpRequest, UserLoginDto userLoginDto) {
		DeviceUsernamePasswordAuthenticationToken authRequest = new DeviceUsernamePasswordAuthenticationToken(
				userLoginDto);

		authRequest.setDetails(authenticationDetailsSource.buildDetails(httpRequest));
		Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

		Integer employeeId = ((DatabaseUserDetails) authentication.getPrincipal()).getUser().getEmployee().getId();
		String userType = employeeId != null ? ARCustomerType.Employer.name() : null;
		onAuthenticationUserType(httpRequest, userType);

		return authentication;
	}

	public void onAuthenticationUserType(HttpServletRequest request, String userType) {
		if (!ARCustomerType.Employer.name().equals(userType)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ResponseDto dto = new ResponseDto(ErrorCodeEnum.NOT_AUTHORIZE, "User is not Employer");
			try {
				response.getWriter().write(mapper.writeValueAsString(dto));
				response.getWriter().flush();
				failureHandler.onAuthenticationFailure(request, response, failed);
			} catch (IOException | ServletException e) {
				e.printStackTrace();
			}
		}
	}
}
