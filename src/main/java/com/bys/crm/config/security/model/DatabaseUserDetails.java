package com.bys.crm.config.security.model;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.bys.crm.config.security.DefaultSecurityRoles;
import com.bys.crm.domain.erp.model.ADUsers;


public class DatabaseUserDetails implements UserDetails{

	private static final long serialVersionUID = -1318195066181066324L;
	
	private Collection<GrantedAuthority> authorities;
		
	private ADUsers user;
	
	private DateTime lastAccessTime;
		
	public DatabaseUserDetails(ADUsers user) {
		this.authorities = DefaultSecurityRoles.getDefaultUserRoles();
		this.user = user;
		this.lastAccessTime = DateTime.now();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

//	public String getName() {
//		return user.getCustomer().getName();
//	}

	public Long getUserId() {
		return user.getId();
	}

	public ADUsers getUser() {
		return user;
	}

	public DateTime getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(DateTime lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
}
