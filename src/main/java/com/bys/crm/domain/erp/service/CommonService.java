package com.bys.crm.domain.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.ARProspectCustomersRepository;

@Service
public class CommonService {

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private ARCustomerContactRepository contactRepository;

	@Autowired
	private ARProspectCustomersRepository prospectRepository;
	
	// Check exist phone number
	public Boolean isExistPhoneNumber(String phone) {
		List<ARProspectCustomers> prospectList = prospectRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomers> customerList = customersRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomerContacts> contactList = contactRepository.findByPhone(phone, AAStatus.Alive.name());

		return !(prospectList.isEmpty() && customerList.isEmpty() && contactList.isEmpty());
	}
}
