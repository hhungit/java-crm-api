package com.bys.crm.config.security.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bys.crm.config.security.model.DatabaseUserDetails;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.ADUsers;
import com.bys.crm.domain.erp.repository.ADUsersRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUserDetailsService.class);
	@Autowired
	private ADUsersRepository adPortalUsesrRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("Load user details by username = " + username);
		ADUsers user = adPortalUsesrRepository.findByUsernameAndStatus(username, AAStatus.Alive.name());
		//Ignore user which is waiting for reset password
		//return user == null || user.getResetToken() != null ? null : new DatabaseUserDetails(user);
		//MTam.le comment for bug, will confirm about this
		return user == null ? null : new DatabaseUserDetails(user);
	}

}
