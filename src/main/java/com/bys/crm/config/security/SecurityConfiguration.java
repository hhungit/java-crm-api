package com.bys.crm.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ImportResource("classpath:security-config.xml")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
}