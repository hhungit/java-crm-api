package com.bys.crm.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class DefaultSecurityRoles {
	
	private static Collection<GrantedAuthority> defaultUserRoles;
	
	public static Collection<GrantedAuthority> getDefaultUserRoles() {
		if (defaultUserRoles == null) {
			defaultUserRoles = new ArrayList<>();
			defaultUserRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		return defaultUserRoles;
	}

}
