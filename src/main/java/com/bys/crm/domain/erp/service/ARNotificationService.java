package com.bys.crm.domain.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.model.ARNotifications;
import com.bys.crm.domain.erp.repository.ARNotificationRepository;

@Service
public class ARNotificationService extends AbstractEntityService<ARNotifications, ARNotificationRepository> {
	
	@Autowired
	ARNotificationRepository repository;

	@Override
	protected ARNotificationRepository getRepository() {
		return repository;
	}
}
