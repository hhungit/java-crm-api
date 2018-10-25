package com.bys.crm.config.security.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bys.crm.config.security.model.UserNameNullPasswordToken;


public class JWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

	public boolean supports(Class<?> authentication) {			
		return UserNameNullPasswordToken.class == authentication;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		try {
			return super.authenticate(authentication);
		}
		catch(Exception e) {
			throw new BadCredentialsException("Invalid token");
		}
		
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		throw new UsernameNotFoundException("User not found " + username);
	}

}
