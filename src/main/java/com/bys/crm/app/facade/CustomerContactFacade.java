package com.bys.crm.app.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bys.crm.app.dto.ChartDto;
import com.bys.crm.app.dto.CustomerContactDto;
import com.bys.crm.app.dto.CustomerContactNoImageDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.app.mapping.CustomerContactMapper;
import com.bys.crm.app.mapping.CustomerContactNoImageMapper;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.app.validation.CommonValidator;
import com.bys.crm.app.validation.ContactValidator;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.constant.ChartType;
import com.bys.crm.domain.erp.model.ARCustomerContactGroups;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomerResources;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.ARCustomerContactGroupsRepository;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.ChartUtil;
import com.bys.crm.util.DateTimeUtil;
import com.bys.crm.util.FileUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class CustomerContactFacade {
	@Autowired
	private CustomerContactMapper mapper;
	
	@Autowired
	private CustomerContactNoImageMapper mapperNoImage;
	

	@Autowired
	private GenericMapper genericMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARCustomerContactRepository customerContactRepository;

	@Autowired
	private ARCustomerContactGroupsRepository groupRepository;

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	@Autowired
	private ContactValidator validator;

	@Autowired
	private CommonValidator commonValidator;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerContactFacade.class);

	// Create customer contact
	@Transactional
	public CustomerContactDto createCustomerContact(CustomerContactDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		// Check exist phone number
		commonValidator.checkExistNumberPhone(dto.getPhone());

		ARCustomerContacts entity = mapper.buildEntity(dto);
		entity.setCreatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedDate(DateTime.now());
		entity.setUpdatedUser(employee.getName());
		// if (entity.getCustomer() == null || entity.getCustomer().getId() == null) {
		// ARCustomers customer = new ARCustomers();
		// customer.setId(Long.valueOf(0));
		// entity.setCustomer(customer);
		// }
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());
		// Get contacts from DTO
		if (dto.getCustomers() != null && !dto.getCustomers().isEmpty()) {
			// Convert contacts from ArrayList to Set
			entity.setCustomerContactGroups(new HashSet<ARCustomerContactGroups>(getCustomersAndInsert(dto, entity)));
		}

		// Save data into DB
		customerContactRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get customer contact by id
	public CustomerContactDto getCustomerContactById(Integer employeeId, Long contactId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		ARCustomerContacts customerContact = customerContactRepository.findByIdAndStatus(contactId,
				AAStatus.Alive.name());

		if (customerContact == null) {
			throw new InvalidException("Customer Contact is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(customerContact, employeeId, groupIds);
	}

	// Edit customer contact
	@Transactional
	public CustomerContactDto editCustomerContact(CustomerContactDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation Input data
		validator.validate(dto);

		// Get customer contact
		ARCustomerContacts contact = customerContactRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

		// Check contact is exist or not
		if (contact == null) {
			throw new InvalidException("Contact is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// If update phone then ...
		if (dto.getPhone() != null && !dto.getPhone().equals(contact.getPhone())) {
			// Check exist phone number
			commonValidator.checkExistNumberPhone(dto.getPhone());
		}

		// Build entity
		contact.setTitle(dto.getTitle());
		contact.setFirstName(dto.getFirstName());
		contact.setLastName(dto.getLastName());
		contact.setBirthday(dto.getBirthday());
		// ARCustomers customer = new ARCustomers();
		// if (dto.getCustomer() == null || dto.getCustomer().getId() == null) {
		// customer.setId(Long.valueOf(0));
		// } else {
		// customer.setId(dto.getCustomer().getId());
		// }
		// contact.setCustomer(customer);
		contact.setPotentialSource(dto.getPotentialSource());
		contact.setJobTitle(dto.getJobTitle());
		contact.setDepartment(dto.getDepartment());
		contact.setPhone(dto.getPhone());
		contact.setCellularPhone(dto.getCellularPhone());
		contact.setHomePhone(dto.getHomePhone());
		contact.setSecondaryPhone(dto.getSecondaryPhone());
		contact.setEmail(dto.getEmail());
		contact.setAssistant(dto.getAssistant());
		contact.setAssistantPhone(dto.getAssistantPhone());
		contact.setAssignedTo(dto.getAssignedTo());
		contact.setAddress(dto.getAddress());
		contact.setDistrict(dto.getDistrict());
		contact.setCity(dto.getCity());
		contact.setCountry(dto.getCountry());
		contact.setInformation(dto.getInformation());
		contact.setImage(dto.getImage());
		contact.setAddress2(dto.getAddress2());
		contact.setDistrict2(dto.getDistrict2());
		contact.setCity2(dto.getCity2());
		contact.setCountry2(dto.getCountry2());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees employeeEntity = new HREmployees();
			employeeEntity.setId(dto.getEmployee().getId());
			contact.setEmployee(employeeEntity);
		} else {
			contact.setEmployee(null);
		}
		contact.setUpdatedDate(DateTime.now());
		contact.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			contact.setEmployeeGroup(group);
		} else {
			contact.setEmployeeGroup(null);
		}
		contact.setUpdatedUserId(employee.getId());

		if (dto.getCustomerResource() != null & dto.getCustomerResource().getId() != null) {
			ARCustomerResources customerResource = new ARCustomerResources();
			customerResource.setId(dto.getCustomerResource().getId());
			contact.setCustomerResource(customerResource);
		} else
			contact.setCustomerResource(null);

		// Get customerContactGroup by contact
		List<ARCustomerContactGroups> customerContactGroups = groupRepository
				.findByContactAndContactStatusAndStatus(contact, AAStatus.Alive.name(), AAStatus.Alive.name());

		if (dto.getCustomers() != null && !dto.getCustomers().isEmpty()) {
			// Get customers and insert to ARCustomerContactGroups
			getCustomersAndInsert(dto, contact);
		}

		// Delete customerContactGroups
		groupRepository.deleteInBatch(customerContactGroups);

		// Save data into DB
		customerContactRepository.save(contact);

		return mapper.buildDto(contact);
	}

	// Delete customer contact
	@Transactional
	public String deleteCustomerContact(Long contactId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check contact is exist or not
		ARCustomerContacts contact = customerContactRepository.findByIdAndStatus(contactId, AAStatus.Alive.name());
		if (contact == null) {
			throw new InvalidException("Contact is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		contact.setStatus(AAStatus.Delete.name());
		contact.setUpdatedDate(DateTime.now());
		contact.setUpdatedUser(employee.getName());
		contact.setUpdatedUserId(employee.getId());

		// Save data into DB
		customerContactRepository.save(contact);

		return StatusMessengerEnum.Successful.name();
	}

	// Delete customer contact list
	@Transactional
	public String deleteCustomerContactList(List<Long> idList, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (idList == null || idList.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARCustomerContacts contactEntity = null;

		// Loop id list
		for (Long id : idList) {
			// Get contact
			contactEntity = customerContactRepository.findByIdAndStatus(id, AAStatus.Alive.name());

			// Check contact is exist or not
			if (contactEntity == null) {
				throw new InvalidException("Contact is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			contactEntity.setStatus(AAStatus.Delete.name());
			contactEntity.setUpdatedDate(DateTime.now());
			contactEntity.setUpdatedUser(employee.getName());
			contactEntity.setUpdatedUserId(employee.getId());

			// Save data into DB
			customerContactRepository.save(contactEntity);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Search contact
	public PageableResult<CustomerContactDto> searchContact(Integer employeeId, String searchKey, Integer pageNumber,
			Integer pageSize, String sortBy, String direct) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(searchKey)) {
			try {
				searchKey = ("%".concat(StringUtils.trim(URLDecoder.decode(searchKey, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder keySearch fail" + e);
			}
		} else {
			searchKey = "%";
		}

		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
				
		// Get page request list with page number and page size.
		Pageable contactPageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "firstName")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		// Search contact by first name, last name, phone, email
		Page<ARCustomerContacts> contactList = customerContactRepository
				.findByFirstNameLikeOrLastNameLikeOrPhoneLikeOrCellularPhoneLikeOrEmailLike(searchKey, searchKey,
						searchKey, searchKey, searchKey, AAStatus.Alive.name(), contactPageRequest);

		// Convert the list of entity to the list of dto
		List<CustomerContactDto> dtos = null;
		if (contactList != null && contactList.getContent() != null && !contactList.getContent().isEmpty()) {
			dtos = contactList.getContent().stream().map(contact -> mapper.buildDto(contact, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, contactList.getTotalPages(), contactList.getTotalElements(), dtos);
	}

	// Import contact from excel file
	@Transactional
	public String importContactCustomerFromExcel(Integer employeeId, MultipartFile file) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from excel file to dto
		List<CustomerContactDto> contactDtos = FileUtil.convertExcelToObject(FileUtil.getUploadPath(file).toString(),
				CustomerContactDto.class, ADObjectType.Contact.name());

		ARCustomerContacts entity = null;
		List<HREmployees> employees = null;
		for (CustomerContactDto dto : contactDtos) {
			// Validation input data
			validator.validate(dto);

			if (StringUtil.isNotEmpty(dto.getEmployeeNo())) {
				// Get employees by employee number
				employees = employeesRepository.findByStatusAndEmployeeNumber(AAStatus.Alive.name(),
						dto.getEmployeeNo());

				// Validation employee number
				if (employees.isEmpty()) {
					throw new InvalidException("Employee number is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
				}

				dto.setEmployee(new EmployeeSummaryDto(employees.get(0).getId()));
			} else {
				dto.setEmployee(null);
			}

			entity = mapper.buildEntity(dto);
			entity.setCreatedDate(DateTime.now());
			entity.setUpdatedDate(DateTime.now());
			entity.setCreatedUser(employee.getName());
			entity.setUpdatedUser(employee.getName());
			// if (entity.getCustomer() == null || entity.getCustomer().getId() == null) {
			// ARCustomers customer = new ARCustomers();
			// customer.setId(Long.valueOf(0));
			// entity.setCustomer(customer);
			// }
			entity.setCreatedUserId(employee.getId());
			entity.setUpdatedUserId(employee.getId());

			// save data into DB
			customerContactRepository.save(entity);
		}

		return StatusMessengerEnum.Successful.name();
	}

	// Search contact
	public List<CustomerContactNoImageDto> searchContactByName(Integer employeeId, String contactName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(contactName)) {
			try {
				contactName = ("%".concat(StringUtils.trim(URLDecoder.decode(contactName, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder contactName fail" + e);
			}
		} else {
			contactName = "%";
		}

		// Search contact by name
		List<ARCustomerContacts> contactList = customerContactRepository
				.findByFirstNameLikeAndStatusOrderByFirstNameAsc(contactName, AAStatus.Alive.name());
		// Convert the list of entity to the list of dto
		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
		
				List<CustomerContactNoImageDto> dtos = null;
				if (contactList != null &&  !contactList.isEmpty()) {
					dtos = contactList.stream().map(contact -> mapperNoImage.buildDto(contact, employeeId, groupIds))
							.collect(Collectors.toList());
				}
		

		return dtos;//  contactList.stream().map(contact -> genericMapper.buildObject(contact, CustomerContactNoImageDto.class))
			  	//.collect(Collectors.toList());
	}

	// Filter contact
	public PageableResult<CustomerContactDto> contactFilter(Integer employeeId, String searchKey,
			Long potentialSourceId, String jobTitle, Integer pageNumber, Integer pageSize, String sortBy, String direct,
			Long fromDate, Long toDate) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		// validator.validateFilter(potentialSource);

		if (StringUtils.isNotBlank(searchKey)) {
			try {
				searchKey = ("%".concat(StringUtils.trim(URLDecoder.decode(searchKey, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder keySearch fail" + e);
			}
		} else {
			searchKey = "%";
		}

		if (StringUtils.isNotBlank(jobTitle)) {
			try {
				jobTitle = "%".concat(StringUtils.trim(URLDecoder.decode(jobTitle, "UTF-8"))).concat("%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder jobTitle fail" + e);
			}
		} else {
			jobTitle = "%";
		}

		// Get page request list with page number and page size.
		Pageable pageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "firstName")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		Page<ARCustomerContacts> contactList = null;
		if (potentialSourceId == null)
			contactList = customerContactRepository
					.findByPotentialSourceLikeAndJobTitleLikeAndStatusNotPotentialSourceId(searchKey, jobTitle,
							fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
							toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
							pageRequest);
		else
			contactList = customerContactRepository.findByPotentialSourceLikeAndJobTitleLikeAndStatus(searchKey,
					potentialSourceId, jobTitle,
					fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
					toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
					pageRequest);

		// Convert the list of entity to the list of dto
		List<CustomerContactDto> dtos = null;
		if (contactList != null && contactList.getContent() != null && !contactList.getContent().isEmpty()) {
			dtos = contactList.getContent().stream().map(contact -> mapper.buildDto(contact, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, contactList.getTotalPages(), contactList.getTotalElements(), dtos);
	}

	@Transactional
	public List<ChartDto> getCustomerContactChart(Long employeeId, Long from, Long to, String type) {
		List<ChartDto> dtos = null;

		DateTime startDate = DateTimeUtil.toDateTimeAtStartOfDay(from);
		DateTime endDate = DateTimeUtil.toDateTimeAtEndOfDay(to);
		if (startDate.isAfter(endDate)) {
			throw new ResourceNotFoundException("End Date must be greater than Start Date.",
					ErrorCodeEnum.INVALID_DATE);
		}

		List<ARCustomerContacts> entities = this.customerContactRepository
				.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(), startDate, endDate);

		Map<DateTime, Long> counting = null;

		if (entities != null && !entities.isEmpty()) {
			if (ChartType.DAY.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCustomerContacts::getCreatedDateChartKeyByDay, Collectors.counting()));
				dtos = counting.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
						.map(e -> new ChartDto(e.getKey(), e.getValue().floatValue())).collect(Collectors.toList());
			} else if (ChartType.MONTH.value().equals(type)) {
				counting = entities.stream().collect(Collectors
						.groupingBy(ARCustomerContacts::getCreatedDateChartKeyByMonth, Collectors.counting()));
				dtos = ChartUtil.buildChartByMonth(startDate, endDate, counting);
			} else {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCustomerContacts::getCreatedDateChartKeyByYear, Collectors.counting()));
				dtos = ChartUtil.buildChartByYear(startDate, endDate, counting);
			}
		}

		return dtos;
	}

	// Check Contact Name is exist
	public int checkContactNameExist(Integer employeeId, String contactName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (customerContactRepository.findByLastNameAndFirstNameAndStatus(contactName, AAStatus.Alive.name())
				.isEmpty()) {
			return 0;
		} else {
			return 1;
		}
	}

	// Get customer contact by id
	public List<CustomerContactDto> getContactByCustomerId(Integer employeeId, Long customerId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validate customer id
		ARCustomers customer = customersRepository.findByIdAndStatus(customerId, AAStatus.Alive.name());
		if (customer == null) {
			throw new InvalidException("Customer id is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		List<ARCustomerContactGroups> customerContactGroups = groupRepository
				.findByCustomerAndCustomerStatusAndStatus(customer, AAStatus.Alive.name(), AAStatus.Alive.name());

		List<ARCustomerContacts> contactList = new ArrayList<>();
		customerContactGroups.forEach(group -> {
			contactList.add(group.getContact());
		});
		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
				List<CustomerContactDto> dtos = null;
				if (contactList != null &&  !contactList.isEmpty()) {
					dtos = contactList.stream().map(contact -> mapper.buildDto(contact, employeeId, groupIds))
							.collect(Collectors.toList());
				}
				
		return dtos;// mapper.buildDtos(contactList);
	}

	// Get customer and insert to ARCustomerContactGroups
	public List<ARCustomerContactGroups> getCustomersAndInsert(CustomerContactDto dto, ARCustomerContacts contact) {
		List<ARCustomerContactGroups> customerContactGroups = new ArrayList<>();

		// Get contact id list
		ArrayList<Long> customerIdList = new ArrayList<>();
		dto.getCustomers().forEach(item -> {
			customerIdList.add(item.getId());
		});

		if (!customerIdList.isEmpty()) {
			// Get customerList from DB
			List<ARCustomers> customerList = customersRepository.findByIds(customerIdList.toArray(new Long[0]));

			// Convert customers from ArrayList to Set
			Set<ARCustomers> customers = new HashSet<ARCustomers>(customerList);

			// Insert data to ARCustomerContactGroups table
			ARCustomerContactGroups customerContactGroup;
			AtomicLong maxId = keyGenerationService.findMaxId(groupRepository);
			for (ARCustomers customer : customers) {
				customerContactGroup = new ARCustomerContactGroups();
				customerContactGroup.setContact(contact);
				customerContactGroup.setStatus(AAStatus.Alive.name());
				customerContactGroup.setCustomer(customer);
				customerContactGroup.setId(maxId.incrementAndGet());
				customerContactGroups.add(customerContactGroup);
			}
			;

			groupRepository.save(customerContactGroups);
		}
		return customerContactGroups;
	}
}
