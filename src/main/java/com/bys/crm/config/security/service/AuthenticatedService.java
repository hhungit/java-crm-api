package com.bys.crm.config.security.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.model.ADUsers;

@Service
public class AuthenticatedService {
	public DatabaseUserDetails getUserDetails() {
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		 Authentication authentication = securityContext == null ? null: securityContext.getAuthentication();		 
		 return authentication == null? null:(DatabaseUserDetails)authentication.getPrincipal();		 
	}
	
	public void authorizeUserPermission(Long userId) {
		DatabaseUserDetails userDetails = this.getUserDetails();
		if (!userId.equals(userDetails.getUserId())) {
			throw new AccessDeniedException("Non permission with user id = " + userId);
		}
	}
	
	public ADUsers getCurrentUser() {
		return getUserDetails().getUser();
	}
	
//	public Long getCustomerId() {
//		return getUserDetails().getUser().getCustomer().getId();
//	}
}
