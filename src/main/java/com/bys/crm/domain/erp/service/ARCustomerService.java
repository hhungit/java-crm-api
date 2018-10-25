package com.bys.crm.domain.erp.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.constant.ARCustomerType;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;

@Service
public class ARCustomerService extends AbstractEntityService<ARCustomers, ARCustomersRepository>{

	@Autowired
	ARCustomersRepository repository;
	
	@Autowired
	private KeyGenerationService keyGenerationService;
	
	@Override
	protected ARCustomersRepository getRepository() {
		return repository;
	}

	public void createCustomer(ARCustomers customer, AtomicLong customerId, AtomicLong userId) {
		customer.setCustomerNumber(keyGenerationService.generateCustomerNumber());
		customer.setId(customerId.incrementAndGet());
		customer.setCustomerType(ARCustomerType.Individual.name());
	}
}
