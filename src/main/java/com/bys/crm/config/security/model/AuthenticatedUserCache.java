package com.bys.crm.config.security.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUserCache implements UserCache {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserCache.class);
	
	private Map<String, UserDetails> cache = new ConcurrentHashMap<>();
	
	@Value("${session-login-expired}")
	private long expiredTime;

	@Override
	public UserDetails getUserFromCache(String username) {	
		DatabaseUserDetails userDetails = (DatabaseUserDetails)cache.get(username);
		if (userDetails == null) {
			return null;
		}
		long idleTime =DateTime.now().getMillis() - userDetails.getLastAccessTime().getMillis();
		LOGGER.info("User has been iddle in {} ms ",idleTime);
		if (expiredTime < idleTime) {
			LOGGER.warn("Session logged in expired....");
			return null;
		}
			
		userDetails.setLastAccessTime(DateTime.now());
		
		return userDetails;
	}

	@Override
	public void putUserInCache(UserDetails user) {
		
		cache.put(user.getUsername(), user);	
		
	}

	@Override
	public void removeUserFromCache(String username) {
		cache.remove(username);	
	}

	public void setExpiredTime(long expiredTime) {
		LOGGER.info("Setting user login expired time = {}", expiredTime );
		this.expiredTime = expiredTime;
	}
	
	public void putUserInCache(String userName,UserDetails user) {
		cache.put(userName, user);	
	}
	

}
