package com.bys.crm.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bys.crm.config.security.jwt.JWTService;
import com.bys.crm.config.security.model.UserNameNullPasswordToken;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	
	protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	public void setJwtService(JWTService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String username = jwtService.decode(token);
			UserNameNullPasswordToken authRequest = new UserNameNullPasswordToken(username);
			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
			Authentication auth = authenticationManager.authenticate(authRequest);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			LOGGER.warn("Cannot authenticate token {}" , token, e);
			filterChain.doFilter(request, response);
		}

	}

}
