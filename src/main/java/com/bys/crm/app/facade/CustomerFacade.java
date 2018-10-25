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
import com.bys.crm.app.dto.CustomerDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.app.mapping.CustomerMapper;
import com.bys.crm.app.validation.CommonValidator;
import com.bys.crm.app.validation.CustomerValidator;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.constant.ChartType;
import com.bys.crm.domain.erp.constant.CustomerType;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ARCustomerContactGroups;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.ARCustomerContactGroupsRepository;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.ChartUtil;
import com.bys.crm.util.DateTimeUtil;
import com.bys.crm.util.FileUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class CustomerFacade {
	@Autowired
	private CustomerMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private ARCustomerContactRepository contactRepository;

	@Autowired
	private ARCustomerContactGroupsRepository groupRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	@Autowired
	private CustomerValidator validator;

	@Autowired
	private CommonValidator commonValidator;
	
	@Autowired
	private EmployeeService employeeService;

	// @Autowired
	// private ADPrivilegeService adPrivilegeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerFacade.class);

	// Create customer
	@Transactional
	public CustomerDto createCustomer(CustomerDto dto, Integer employeeId, String url) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check privilege
		// if (!adPrivilegeService.checkPrivilege(employee.getPrivilegeGroups(),
		// ADPrivilegeName.Add.name(), url)) {
		// throw new InvalidException("Employee have not permission for this
		// action", ErrorCodeEnum.NOT_PERMISSION);
		// }

		// Validation input data
		validator.validate(dto);

		// Check exist phone number
		commonValidator.checkExistNumberPhone(dto.getTel1());

		ARCustomers entity = mapper.buildEntity(dto);
		entity.setCreatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedDate(DateTime.now());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());

		// Get contacts from DTO
		if (dto.getContacts() != null && !dto.getContacts().isEmpty()) {
			// Convert contacts from ArrayList to Set
			entity.setCustomerContactGroups(new HashSet<ARCustomerContactGroups>(getContactsAndInsert(dto, entity)));
		}

		customersRepository.save(entity);

		return mapper.buildDto(entity);

	}

	// Get customer by id
	public CustomerDto getCustomerById(Integer employeeId, Long customerId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		ARCustomers customer = customersRepository.findByIdAndStatus(customerId, AAStatus.Alive.name());

		if (customer == null) {
			throw new InvalidException("Customer is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}
		 
		return mapper.buildDto(customer, employeeId, groupIds);
	}

	// Edit customer
	@Transactional
	public CustomerDto editCustomer(CustomerDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation Input data
		validator.validate(dto);

		// Validation id
		if (dto.getId() == null) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get customer
		ARCustomers customer = customersRepository.findOne(dto.getId());

		// Check customer is exist or not
		if (customer == null || !customer.getStatus().equalsIgnoreCase(AAStatus.Alive.name())) {
			throw new InvalidException("Customer is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// If update phone then ...
		if (!dto.getTel1().equals(customer.getTel1())) {
			// Check exist phone number
			commonValidator.checkExistNumberPhone(dto.getTel1());
		}

		// Build entity
		customer.setName(dto.getName());
		customer.setWebsite(dto.getWebsite());
		customer.setFax(dto.getFax());
		customer.setTel1(dto.getTel1());
		customer.setTel2(dto.getTel2());
		customer.setStockCode(dto.getStockCode());
		customer.setTaxNumber(dto.getTaxNumber());
		customer.setEmail(dto.getEmail());
		customer.setEmail2(dto.getEmail2());
		customer.setBusiness(dto.getBusiness());
		customer.setGroup(dto.getGroup());
		customer.setEvaluate(dto.getEvaluate());
		customer.setClassify(dto.getClassify());
		customer.setRevenueDueYear(dto.getRevenueDueYear());
		customer.setAssignedTo(dto.getAssignedTo());
		customer.setAddress(dto.getAddress());
		customer.setCity(dto.getCity());
		customer.setCountry(dto.getCountry());
		customer.setDistrict(dto.getDistrict());
		customer.setInformation(dto.getInformation());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees assignedTo = new HREmployees();
			assignedTo.setId(dto.getEmployee().getId());
			customer.setEmployee(assignedTo);
		} else {
			customer.setEmployee(null);
		}
		customer.setUpdatedDate(DateTime.now());
		customer.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			customer.setEmployeeGroup(group);
		} else {
			customer.setEmployeeGroup(null);
		}
		customer.setCustomerType(dto.getCustomerType());
		customer.setDob(CustomerType.Company.name().equals(dto.getCustomerType()) ? null : dto.getDob());
		customer.setCompanyEstablishmentDay(
				!CustomerType.Company.name().equals(dto.getCustomerType()) ? null : dto.getCompanyEstablishmentDay());
		customer.setUpdatedUserId(employee.getId());
		
		customer.setGender(dto.getGender() == null ? null : Gender.valueOf(dto.getGender()));
		
		customer.setLunarBirthday(dto.getLunarBirthday());

		// Get customerContactGroup by customer
		List<ARCustomerContactGroups> customerContactGroups = groupRepository
				.findByCustomerAndCustomerStatusAndStatus(customer, AAStatus.Alive.name(), AAStatus.Alive.name());

		if (dto.getContacts() != null && !dto.getContacts().isEmpty()) {
			// Get contacts and insert to ARCustomerContactGroups
			getContactsAndInsert(dto, customer);
		}

		// Delete customerContactGroups
		groupRepository.deleteInBatch(customerContactGroups);

		// Save data into DB
		customersRepository.save(customer);

		return mapper.buildDto(customer);
	}

	// Delete customer
	@Transactional
	public String deleteCustomer(Long customerId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check customer is exist or not
		ARCustomers customer = customersRepository.findByIdAndStatus(customerId, AAStatus.Alive.name());
		if (customer == null) {
			throw new InvalidException("Contact is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		customer.setStatus(AAStatus.Delete.name());
		customer.setUpdatedDate(DateTime.now());
		customer.setUpdatedUser(employee.getName());
		customer.setUpdatedUserId(employee.getId());

		// Save data into DB
		customersRepository.save(customer);

		return StatusMessengerEnum.Successful.name();
	}

	// Delete customer list
	@Transactional
	public String deleteCustomerList(List<Long> idList, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (idList == null || idList.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARCustomers customerEntity = null;

		// Loop id list
		for (Long id : idList) {
			// Get customer
			customerEntity = customersRepository.findByIdAndStatus(id, AAStatus.Alive.name());

			// Check customer is exist or not
			if (customerEntity == null) {
				throw new InvalidException("Customer is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			customerEntity.setStatus(AAStatus.Delete.name());
			customerEntity.setUpdatedDate(DateTime.now());
			customerEntity.setUpdatedUser(employee.getName());
			customerEntity.setUpdatedUserId(employee.getId());

			// Save data into DB
			customersRepository.save(customerEntity);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Import customer from excel file
	@Transactional
	public String importCustomerFromExcel(Integer employeeId, MultipartFile file) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from excel file to customer dto
		List<CustomerDto> customerDtos = FileUtil.convertExcelToObject(FileUtil.getUploadPath(file).toString(),
				CustomerDto.class, ADObjectType.Customer.name());

		ARCustomers entity = null;
		List<HREmployees> employees = null;
		for (CustomerDto dto : customerDtos) {
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
			entity.setCreatedUser(employee.getName());
			entity.setUpdatedDate(DateTime.now());
			entity.setUpdatedUser(employee.getName());
			entity.setCreatedUserId(employee.getId());
			entity.setUpdatedUserId(employee.getId());

			// save data into DB
			customersRepository.save(entity);
		}

		return StatusMessengerEnum.Successful.name();
	}

	// Search customer
	public PageableResult<CustomerDto> searchCustomer(Integer employeeId, String searchKey, Integer pageNumber,
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
		Pageable customerPageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "name")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));
		// Search customer by name, phone, email, website with searchKey
		Page<ARCustomers> customerList = customersRepository.findByNameLikeOrTel1LikeOrEmailLikeOrWebsiteLike(searchKey,
				searchKey, searchKey, searchKey, searchKey, AAStatus.Alive.name(), customerPageRequest);
	
		
		// Convert the list of entity to the list of dto
		List<CustomerDto> dtos = null;
		if (customerList != null && customerList.getContent() != null && !customerList.getContent().isEmpty()) {
		
			dtos = customerList.getContent().stream().map(customer -> mapper.buildDto(customer, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, customerList.getTotalPages(), customerList.getTotalElements(), dtos);
	}

	// Search customer
	public List<CustomerDto> searchCustomerByName(Integer employeeId, String customerName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(customerName)) {
			try {
				customerName = ("%".concat(StringUtils.trim(URLDecoder.decode(customerName, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder customerName fail" + e);
			}
		} else {
			customerName = "%";
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
		
		// Search customer by name
		List<ARCustomers> customerList = customersRepository.findByNameLikeAndStatusOrderByNameAsc(customerName,
				AAStatus.Alive.name());
	 
		
		// Convert the list of entity to the list of dto
		List<CustomerDto> dtos = null;
		if (customerList != null &&   !customerList.isEmpty()) {
			dtos = customerList.stream().map(customer -> mapper.buildDto(customer, employeeId, groupIds))
					.collect(Collectors.toList());
		}


		return dtos;// mapper.buildDtos(customerList);
	}

	// Filter customer
	public PageableResult<CustomerDto> customerFilter(Integer employeeId, String searchKey, String business,
			String group, Long evaluate, Integer pageNumber, Integer pageSize, String sortBy, String direct,
			Long fromDate, Long toDate) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		validator.validateFilter(business, group);

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

		// Get page request list with page number and page size.
		Pageable pageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "name")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		// Search customer with filter
		Page<ARCustomers> customerList = customersRepository.findByBusinessLikeAndGroupLikeAndEvaluateLikeAndStatus(
				searchKey, StringUtil.convertSearchKey(business), StringUtil.convertSearchKey(group),
				evaluate == null ? new Long[] { 0L } : new Long[] { evaluate },
				fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
				toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
				pageRequest);

		// Convert the list of entity to the list of dto
		List<CustomerDto> dtos = null;
		if (customerList != null && customerList.getContent() != null && !customerList.getContent().isEmpty()) {
			 
			dtos = customerList.getContent().stream().map(customer -> mapper.buildDto(customer, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, customerList.getTotalPages(), customerList.getTotalElements(), dtos);
	}

	@Transactional
	public List<ChartDto> getCustomerChart(Integer employeeId, Long from, Long to, String type) {
		List<ChartDto> dtos = null;

		// Validate employee id
		if (employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId) == null) {
			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		DateTime startDate = DateTimeUtil.toDateTimeAtStartOfDay(from);
		DateTime endDate = DateTimeUtil.toDateTimeAtEndOfDay(to);
		if (startDate.isAfter(endDate)) {
			throw new ResourceNotFoundException("End Date must be greater than Start Date.",
					ErrorCodeEnum.INVALID_DATE);
		}

		List<ARCustomers> entities = this.customersRepository
				.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(), startDate, endDate);

		Map<DateTime, Long> counting = null;

		if (entities != null && !entities.isEmpty()) {
			if (ChartType.DAY.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCustomers::getCreatedDateChartKeyByDay, Collectors.counting()));
				dtos = counting.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
						.map(e -> new ChartDto(e.getKey(), e.getValue().floatValue())).collect(Collectors.toList());
			} else if (ChartType.MONTH.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCustomers::getCreatedDateChartKeyByMonth, Collectors.counting()));
				dtos = ChartUtil.buildChartByMonth(startDate, endDate, counting);
			} else {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCustomers::getCreatedDateChartKeyByYear, Collectors.counting()));
				dtos = ChartUtil.buildChartByYear(startDate, endDate, counting);
			}
		}

		return dtos;
	}

	// Check Customer Name is exist
	public int checkCustomerNameExist(Integer employeeId, String customerName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (customersRepository.findByNameAndStatus(customerName, AAStatus.Alive.name()).isEmpty()) {
			return 0;
		} else {
			return 1;
		}
	}

	// Get contacts and insert to ARCustomerContactGroups
	public List<ARCustomerContactGroups> getContactsAndInsert(CustomerDto dto, ARCustomers customer) {
		List<ARCustomerContactGroups> customerContactGroups = new ArrayList<>();

		// Get contact id list
		ArrayList<Long> contactIdList = new ArrayList<>();
		dto.getContacts().forEach(item -> {
			contactIdList.add(item.getId());
		});

		if (!contactIdList.isEmpty()) {
			// Get contactList from DB
			List<ARCustomerContacts> contactList = contactRepository.findByIds(contactIdList.toArray(new Long[0]));

			// Convert contacts from ArrayList to Set
			Set<ARCustomerContacts> contacts = new HashSet<ARCustomerContacts>(contactList);

			// Insert data to ARCustomerContactGroups table
			ARCustomerContactGroups customerContactGroup;
			AtomicLong maxId = keyGenerationService.findMaxId(groupRepository);
			for (ARCustomerContacts contact : contacts) {
				customerContactGroup = new ARCustomerContactGroups();
				customerContactGroup.setCustomer(customer);
				customerContactGroup.setStatus(AAStatus.Alive.name());
				customerContactGroup.setContact(contact);
				customerContactGroup.setId(maxId.incrementAndGet());
				customerContactGroups.add(customerContactGroup);
			}
			;

			groupRepository.save(customerContactGroups);
		}
		return customerContactGroups;
	}

	// get Customer By contactId
	public List<CustomerDto> getCustomerByContactId(Integer employeeId, Long contactId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validate contact id
		ARCustomerContacts contact = contactRepository.findByIdAndStatus(contactId, AAStatus.Alive.name());
		if (contact == null) {
			throw new InvalidException("Contact id is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		List<ARCustomerContactGroups> customerContactGroups = groupRepository
				.findByContactAndContactStatusAndStatus(contact, AAStatus.Alive.name(), AAStatus.Alive.name());

		List<ARCustomers> customerList = new ArrayList<>();
		customerContactGroups.forEach(group -> {
			customerList.add(group.getCustomer());
		});

		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
				
				// Convert the list of entity to the list of dto
				List<CustomerDto> dtos = null;
				if (customerList != null && !customerList.isEmpty()) {
					 
					dtos = customerList.stream().map(customer -> mapper.buildDto(customer, employeeId, groupIds))
							.collect(Collectors.toList());
				}
				
		return dtos;// mapper.buildDtos(customerList);
	}
}
