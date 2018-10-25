package com.bys.crm.app.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.ObjectDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.validation.BranchValidator;
import com.bys.crm.app.validation.EmployeeValidator;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.ARProspectCustomersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class CommonFacade {

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private ARCustomerContactRepository contactRepository;

	@Autowired
	private ARProspectCustomersRepository prospectRepository;

	@Autowired
	private EmployeeValidator employeeValidator;

	@Autowired
	private BranchValidator branchValidator;

	// Search customer
	public List<ObjectDto> searchPhoneNumber(Integer employeeId, String phone) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<ARProspectCustomers> prospectList = prospectRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomers> customerList = customersRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomerContacts> contactList = contactRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ObjectDto> objectList = new ArrayList<>();

		if (!prospectList.isEmpty()) {
			prospectList.forEach(prospect -> {
				objectList.add(new ObjectDto(prospect.getLastName() + " " + prospect.getFirstName(),
						prospect.getAddress(), ADObjectType.Prospect.name(), prospect.getId(), prospect.getEmail(), prospect.getCompany(), null,new BigDecimal(0)));
			});
		}

		if (!customerList.isEmpty()) {
			customerList.forEach(customer -> {
				objectList.add(new ObjectDto(customer.getName(), customer.getAddress(), ADObjectType.Customer.name(),
						customer.getId(), customer.getEmail(), null, customer.getWebsite(), customer.getOwing()));
			});
		}

		if (!contactList.isEmpty()) {
			contactList.forEach(contact -> {
				objectList.add(new ObjectDto(contact.getLastName() + " " + contact.getFirstName(), contact.getAddress(),
						ADObjectType.Contact.name(), contact.getId(), contact.getEmail()));
			});
		}

		return objectList;
	}

	// Check exist phone number
	public Boolean isExistPhoneNumber(Integer employeeId, Integer branchId, String phone) {
		// Validate employee id
		employeeValidator.validateEmployeeId(employeeId);

		// Validate branch id
		branchValidator.validateBranchId(branchId);

		List<ARProspectCustomers> prospectList = prospectRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomers> customerList = customersRepository.findByPhone(phone, AAStatus.Alive.name());

		List<ARCustomerContacts> contactList = contactRepository.findByPhone(phone, AAStatus.Alive.name());

		return !(prospectList.isEmpty() && customerList.isEmpty() && contactList.isEmpty());
	}
}
