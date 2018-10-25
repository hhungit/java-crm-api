package com.bys.crm.config.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.bys.crm.app.dto.IsLeaderDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.dto.UserInfoDto;
import com.bys.crm.config.security.jwt.JWTService;
import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.model.ADUserDevices;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceUsernamePasswordLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private JWTService jwtService;
	private ObjectMapper mapper = new ObjectMapper();
	
	
		
	public void setJwtService(JWTService jwtService) {
		this.jwtService = jwtService;
	}

//	@Autowired
//	private ADPrivilegeService adPrivilegeService;

	@Autowired
	private EmployeeService employeeService;

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		
		DatabaseUserDetails userDetails = (DatabaseUserDetails) auth.getPrincipal();
		
		ADUserDevices device = userDetails.getUser().getAuthenticatedDevice();
		String loginName = (device == null) ? userDetails.getUsername()
				: SecurityUtil.getLoginName(userDetails.getUsername(), device.getDeviceKey(), device.getDeviceType());
		
		// Get privilege detail list
//		List<PrivilegeDetailDto> privilegeDetails = adPrivilegeService.getPrivilegeDetailList(userDetails.getUser().getObjectID().intValue());
		
//		UserInfoDto dto = new UserInfoDto(jwtService.encode(loginName), userDetails.getUser(), privilegeDetails);
		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(userDetails.getUser().getEmployee().getId());
		List<IsLeaderDto> isLeaderDtos = new ArrayList<>();
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeaderDtos.add(new IsLeaderDto(employeeGroups.get(0).getBranchId(), employeeGroups.get(0).getIsLeader()));
		}
		UserInfoDto dto = new UserInfoDto(jwtService.encode(loginName), userDetails.getUser(), isLeaderDtos);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(new ResponseDto(dto)));
		
		
		response.getWriter().flush();
	}
}
