package com.bys.crm.app.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

import com.bys.crm.app.dto.ChangeProspectDto;
import com.bys.crm.app.dto.ChartDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.ProspectCustomerDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.app.mapping.ProspectCustomerMapper;
import com.bys.crm.app.validation.ChangeProspectValidator;
import com.bys.crm.app.validation.CommonValidator;
import com.bys.crm.app.validation.ProspectCustomerValidator;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.constant.ChartType;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomerResources;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.ARCampaignsRepository;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.ARProspectCustomersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.ChartUtil;
import com.bys.crm.util.DateTimeUtil;
import com.bys.crm.util.FileUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class ProspectCustomerFacade {
	@Autowired
	private ProspectCustomerMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARProspectCustomersRepository prospectCustomersRepository;

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private ARCustomerContactRepository customerContactRepository;

	@Autowired
	private ARCampaignsRepository campaignsRepository;

	@Autowired
	private ProspectCustomerValidator validator;

	@Autowired
	private ChangeProspectValidator changeProspectValidator;

	@Autowired
	private KeyGenerationService keyGenerationService;

	@Autowired
	private CommonValidator commonValidator;
	
	@Autowired
	private EmployeeService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProspectCustomerFacade.class);

	// Create prospect
	@Transactional
	public ProspectCustomerDto createProspectCustomer(ProspectCustomerDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		// Check exist phone number
		commonValidator.checkExistNumberPhone(dto.getPhone());

		// Convert data from dto to entity
		ARProspectCustomers entity = mapper.buildEntity(dto);
		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());

		// Save data into DB
		prospectCustomersRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get prospect by id
	public ProspectCustomerDto getProspectCustomerById(Integer employeeId, Long prospectId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
		
		
		
		ARProspectCustomers prospectCustomer = prospectCustomersRepository.findByIdAndStatus(prospectId,
				AAStatus.Alive.name());

		if (prospectCustomer == null) {
			throw new InvalidException("Prospect is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(prospectCustomer, employeeId, groupIds);
	}

	// Edit prospect
	@Transactional
	public ProspectCustomerDto editProspectCustomer(ProspectCustomerDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation id
		if (dto.getId() == null) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		// Get prospectCustomer
		ARProspectCustomers prospectCustomer = prospectCustomersRepository.findByIdAndStatus(dto.getId(),
				AAStatus.Alive.name());

		// Check Prospect is exist or not
		if (prospectCustomer == null) {
			throw new InvalidException("Prospect is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// If update phone then ...
		if (!dto.getPhone().equals(prospectCustomer.getPhone())) {
			// Check exist phone number
			commonValidator.checkExistNumberPhone(dto.getPhone());
		}

		// Build entity
		prospectCustomer.setTitle(dto.getTitle());
		prospectCustomer.setFirstName(dto.getFirstName());
		prospectCustomer.setLastName(dto.getLastName());
		prospectCustomer.setRate(dto.getRate());
		prospectCustomer.setCompany(dto.getCompany());
//		prospectCustomer.setPotentialSource(dto.getPotentialSource());
//		prospectCustomer.setBusiness(dto.getBusiness());
		prospectCustomer.setRevenue(dto.getRevenue());
		prospectCustomer.setPhone(dto.getPhone());
		prospectCustomer.setCellPhone(dto.getCellPhone());
		prospectCustomer.setEmail(dto.getEmail());
		prospectCustomer.setWebsite(dto.getWebsite());
		prospectCustomer.setPotentialStatus(dto.getPotentialStatus());
		prospectCustomer.setAssign(dto.getAssign());
		prospectCustomer.setAddress(dto.getAddress());
		prospectCustomer.setCountry(dto.getCountry());
		prospectCustomer.setCity(dto.getCity());
		prospectCustomer.setDistrict(dto.getDistrict());
		prospectCustomer.setDescription(dto.getDescription());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees assignedTo = new HREmployees();
			assignedTo.setId(dto.getEmployee().getId());
			prospectCustomer.setEmployee(assignedTo);
		} else {
			prospectCustomer.setEmployee(null);
		}
		prospectCustomer.setUpdatedDate(DateTime.now());
		prospectCustomer.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			prospectCustomer.setEmployeeGroup(group);
		} else {
			prospectCustomer.setEmployeeGroup(null);
		}
		prospectCustomer.setUpdatedUserId(employee.getId());
		prospectCustomer.setObjectType(dto.getObjectType());
		prospectCustomer.setObjectId(dto.getObjectId());
		prospectCustomer.setGender(dto.getGender() == null ? null : Gender.valueOf(dto.getGender()));
		prospectCustomer.setLunarBirthday(dto.getLunarBirthday());
		prospectCustomer.setDob(dto.getDob());
		if (dto.getCustomerResource() != null & dto.getCustomerResource().getId() != null) {
			ARCustomerResources customerResource = new ARCustomerResources();
			customerResource.setId(dto.getCustomerResource().getId());
			prospectCustomer.setCustomerResource(customerResource);
		} else
			prospectCustomer.setCustomerResource(null);
		// Save data into DB
		prospectCustomersRepository.save(prospectCustomer);

		return mapper.buildDto(prospectCustomer);
	}

	// Delete prospectCustomer
	@Transactional
	public String deleteProspectCustomer(Long prospectCustomerId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check prospectCustomer is exist or not
		ARProspectCustomers prospectCustomer = prospectCustomersRepository.findByIdAndStatus(prospectCustomerId,
				AAStatus.Alive.name());
		if (prospectCustomer == null) {
			throw new InvalidException("Prospect is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		prospectCustomer.setStatus(AAStatus.Delete.name());
		prospectCustomer.setUpdatedDate(DateTime.now());
		prospectCustomer.setUpdatedUser(employee.getName());
		prospectCustomer.setUpdatedUserId(employee.getId());

		// Save data into DB
		prospectCustomersRepository.save(prospectCustomer);

		return StatusMessengerEnum.Successful.name();
	}

	// Delete prospectCustomer list
	@Transactional
	public String deleteProspectCustomerList(List<Long> idList, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (idList == null || idList.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARProspectCustomers prospectCustomerEntity = null;

		// Loop id list
		for (Long id : idList) {
			// Get prospectCustomer
			prospectCustomerEntity = prospectCustomersRepository.findByIdAndStatus(id, AAStatus.Alive.name());

			// Check prospectCustomer is exist or not
			if (prospectCustomerEntity == null) {
				throw new InvalidException("Prospect is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			prospectCustomerEntity.setStatus(AAStatus.Delete.name());
			prospectCustomerEntity.setUpdatedDate(DateTime.now());
			prospectCustomerEntity.setUpdatedUser(employee.getName());
			prospectCustomerEntity.setUpdatedUserId(employee.getId());

			// Save data into DB
			prospectCustomersRepository.save(prospectCustomerEntity);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Import prospect from excel file
	@Transactional
	public String importProspectCustomerFromExcel(Integer employeeId, MultipartFile file) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from excel file to dto
		List<ProspectCustomerDto> prospectCustomerDtos = FileUtil.convertExcelToObject(
				FileUtil.getUploadPath(file).toString(), ProspectCustomerDto.class, ADObjectType.Prospect.name());

		ARProspectCustomers entity = null;
		List<HREmployees> employees = null;
		List<ARCampaigns> campaigns = null;
		for (ProspectCustomerDto dto : prospectCustomerDtos) {
			// Validation input data
			validator.validate(dto);

			// Check exist phone number
			commonValidator.checkExistNumberPhone(dto.getPhone());

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

			// Get campaign by campaign No.
			if (StringUtil.isNotEmpty(dto.getCampaignNo())) {
				// Get campaigns by campaign No.
				campaigns = campaignsRepository.findByStatusAndCampaignNo(AAStatus.Alive.name(), dto.getCampaignNo());

				// Validation campaign No.
				if (campaigns.isEmpty()) {
					throw new InvalidException("Campaign No. is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
				}

				dto.setObjectType(ADObjectType.Campaign.name());
				dto.setObjectId(campaigns.get(0).getId().intValue());
			}

			
			
			entity = mapper.buildEntity(dto);
			if(dto.getFirstName()!= null) {
				String  fullName = dto.getFirstName().trim();
				
				int findFistIndex = fullName.indexOf(" ");
				if(findFistIndex == -1 ) {
					entity.setFirstName(dto.getFirstName());
					entity.setLastName(dto.getFirstName());
				}else {
					String lastName = fullName.substring(0,findFistIndex).trim();
					String firstName = fullName.substring(findFistIndex,fullName.length()).trim();
					entity.setFirstName(firstName);
					entity.setLastName(lastName);
				}
			}
			entity.setCreatedDate(DateTime.now());
			entity.setUpdatedDate(DateTime.now());
			entity.setCreatedUser(employee.getName());
			entity.setUpdatedUser(employee.getName());
			entity.setCreatedUserId(employee.getId());
			entity.setUpdatedUserId(employee.getId());

			// save data into DB
			prospectCustomersRepository.save(entity);
		}

		return StatusMessengerEnum.Successful.name();
	}

	// Create prospect
	@Transactional
	public String changeProspectToCustomerContact(ChangeProspectDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation Input data
		changeProspectValidator.validate(dto);

		// Check prospectCustomer is exist or not
		ARProspectCustomers prospectEntity = prospectCustomersRepository.findByIdAndStatus(dto.getId(),
				AAStatus.Alive.name());
		if (prospectEntity == null) {
			throw new InvalidException("Prospect is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		HREmployees employeeEntity = null;
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			// Check employee is exist or not
			employeeEntity = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), dto.getEmployee().getId());
			if (employeeEntity == null) {
				throw new InvalidException("Employee is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}
		}

		// Update status to delete
		prospectEntity.setStatus(AAStatus.Delete.name());
		prospectEntity.setUpdatedDate(DateTime.now());
		prospectEntity.setUpdatedUser(employee.getName());
		prospectCustomersRepository.save(prospectEntity);

		Long customerId = keyGenerationService.findMaxId(customersRepository).incrementAndGet();
		if (dto.getCreateCustomer() == 1) {
			// Setting customer entity
			ARCustomers customerEntity = new ARCustomers();
			customerEntity.setId(customerId);
			customerEntity.setCustomerNumber(keyGenerationService.generateCustomerNumber());
			customerEntity.setName(dto.getCustomerName());
			customerEntity.setBusiness(dto.getBusiness());
			customerEntity.setCreatedDate(DateTime.now());
			customerEntity.setUpdatedDate(DateTime.now());
			customerEntity.setCreatedUser(employee.getName());
			customerEntity.setUpdatedUser(employee.getName());
			if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
				HREmployees assignedTo = new HREmployees();
				assignedTo.setId(dto.getEmployee().getId());
				customerEntity.setEmployee(assignedTo);
			} else {
				customerEntity.setEmployee(null);
			}
			if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
				HRGroups group = new HRGroups();
				group.setId(dto.getEmployeeGroup().getId());
				customerEntity.setEmployeeGroup(group);
			} else {
				customerEntity.setEmployeeGroup(null);
			}
			customerEntity.setProspect(prospectEntity);
			customerEntity.setCustomerType(dto.getCustomerType());
			customerEntity.setChangedUser(employee.getName());

			// add data from prospect entity
			customerEntity.setWebsite(prospectEntity.getWebsite());
			customerEntity.setTel1(prospectEntity.getPhone());
			customerEntity.setTel2(prospectEntity.getCellPhone());
			customerEntity.setEmail(prospectEntity.getEmail());
			customerEntity.setRevenueDueYear(prospectEntity.getRevenue());
			customerEntity.setAddress(prospectEntity.getAddress());
			customerEntity.setCity(prospectEntity.getCity());
			customerEntity.setCountry(prospectEntity.getCountry());
			customerEntity.setDistrict(prospectEntity.getDistrict());
			customerEntity.setInformation(prospectEntity.getDescription());
			customerEntity.setDob(prospectEntity.getDob());

			// Create customer
			customersRepository.save(customerEntity);
		}

		if (dto.getCreateContact() == 1) {
			// Setting contact entity
			ARCustomerContacts contactEntity = new ARCustomerContacts();
			contactEntity.setId(keyGenerationService.findMaxId(customerContactRepository).incrementAndGet());
			contactEntity.setTitle(dto.getTitle());
			contactEntity.setFirstName(dto.getFirstName());
			contactEntity.setLastName(dto.getLastName());
			contactEntity.setEmail(dto.getEmail());
			contactEntity.setCreatedDate(DateTime.now());
			contactEntity.setUpdatedDate(DateTime.now());
			contactEntity.setCreatedUser(employee.getName());
			contactEntity.setUpdatedUser(employee.getName());
			if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
				HREmployees assignedTo = new HREmployees();
				assignedTo.setId(dto.getEmployee().getId());
				contactEntity.setEmployee(assignedTo);
			} else {
				contactEntity.setEmployee(null);
			}
			if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
				HRGroups group = new HRGroups();
				group.setId(dto.getEmployeeGroup().getId());
				contactEntity.setEmployeeGroup(group);
			} else {
				contactEntity.setEmployeeGroup(null);
			}
			contactEntity.setProspect(prospectEntity);

			// add data from prospect entity
			customerId = dto.getCreateCustomer() == 1 ? customerId : Long.valueOf(0);
			// ARCustomers customerEntity = new ARCustomers();
			// customerEntity.setId(customerId);
			// contactEntity.setCustomer(customerEntity);
			contactEntity.setPotentialSource(prospectEntity.getPotentialSource());
			contactEntity.setPhone(prospectEntity.getPhone());
			contactEntity.setCellularPhone(prospectEntity.getCellPhone());
			contactEntity.setEmail(prospectEntity.getEmail());
			contactEntity.setAddress(prospectEntity.getAddress());
			contactEntity.setDistrict(prospectEntity.getDistrict());
			contactEntity.setCity(prospectEntity.getCity());
			contactEntity.setCountry(prospectEntity.getCountry());
			contactEntity.setInformation(prospectEntity.getDescription());

			// Create contact
			customerContactRepository.save(contactEntity);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Get prospect list with paging
	public PageableResult<ProspectCustomerDto> searchProspect(Integer employeeId, String searchKey, Integer pageNumber,
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

		// Get page request list with page number and page size.
		Pageable prospectPageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "firstName")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));
		// Search prospect by first name, last name, phone, cell phone, email
		// with searchKey
		Page<ARProspectCustomers> prospectCustomerList = prospectCustomersRepository
				.findByFirstNameLikeOrLastNameLikeOrPhoneLikeOrCellPhoneLikeOrEmailLike(searchKey, searchKey, searchKey,
						searchKey, searchKey, AAStatus.Alive.name(), prospectPageRequest);

		// Convert the list of entity to the list of dto

		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

				 
		List<ProspectCustomerDto> dtos = null;
		if (prospectCustomerList != null && prospectCustomerList.getContent() != null
				&& !prospectCustomerList.getContent().isEmpty()) {
			dtos = prospectCustomerList.getContent().stream().map(prospect -> mapper.buildDto(prospect, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, prospectCustomerList.getTotalPages(),
				prospectCustomerList.getTotalElements(), dtos);
	}

	// Search prospect
	public List<ProspectCustomerDto> searchProspectByName(Integer employeeId, String prospectName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(prospectName)) {
			try {
				prospectName = ("%".concat(StringUtils.trim(URLDecoder.decode(prospectName, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder prospectName fail" + e);
			}
		} else {
			prospectName = "%";
		}

		// Search prospect by name
		List<ARProspectCustomers> prospectList = prospectCustomersRepository
				.findByFirstNameLikeAndStatusOrderByFirstNameAsc(prospectName, AAStatus.Alive.name());

	 
		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
				List<ProspectCustomerDto> dtos = null;
				if (prospectList != null
						&& !prospectList.isEmpty()) {
					dtos = prospectList.stream().map(prospect -> mapper.buildDto(prospect, employeeId, groupIds))
							.collect(Collectors.toList());
				}	
				
		return dtos;
	}

	// Filter prospect
	public PageableResult<ProspectCustomerDto> prospectFilter(Integer employeeId, String searchKey, String rate,
			Long prospectSourceId, String business, Integer pageNumber, Integer pageSize, String sortBy, String direct,
			Long fromDate, Long toDate) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		validator.validateFilter(rate, business);

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
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "firstName")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		Page<ARProspectCustomers> prospectList = null;

		if (prospectSourceId == null) {
			// Search prospect with filter
			prospectList = prospectCustomersRepository
					.findByRateLikeAndPotentialSourceLikeAndBusinessLikeAndStatusNotPotentialSourceId(searchKey,
							StringUtil.convertSearchKey(rate), StringUtil.convertSearchKey(business),
							fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
							toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
							pageRequest);
		} else {
			// Search prospect with filter
			prospectList = prospectCustomersRepository.findByRateLikeAndPotentialSourceLikeAndBusinessLikeAndStatus(
					searchKey, StringUtil.convertSearchKey(rate), prospectSourceId,
					StringUtil.convertSearchKey(business),
					fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
					toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
					pageRequest);
		}


	 
		// Convert the list of entity to the list of dto
		List<ProspectCustomerDto> dtos = null;
		if (prospectList != null && prospectList.getContent() != null && !prospectList.getContent().isEmpty()) {
			dtos = prospectList.getContent().stream().map(prospect -> mapper.buildDto(prospect, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, prospectList.getTotalPages(), prospectList.getTotalElements(), dtos);
	}

	// Get prospect by id
	public ProspectCustomerDto getDeletedProspectById(Integer employeeId, Long prospectId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARProspectCustomers prospectCustomer = prospectCustomersRepository.findByIdAndStatus(prospectId,
				AAStatus.Delete.name());

		if (prospectCustomer == null) {
			throw new InvalidException("Prospect is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
				ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());
		
		return mapper.buildDto(prospectCustomer, employeeId, groupIds);
	}

	@Transactional
	public List<ChartDto> getProspectCustomerChart(Long employeeId, Long from, Long to, String type) {
		List<ChartDto> dtos = null;

		DateTime startDate = DateTimeUtil.toDateTimeAtStartOfDay(from);
		DateTime endDate = DateTimeUtil.toDateTimeAtEndOfDay(to);
		if (startDate.isAfter(endDate)) {
			throw new ResourceNotFoundException("End Date must be greater than Start Date.",
					ErrorCodeEnum.INVALID_DATE);
		}

		List<ARProspectCustomers> entities = this.prospectCustomersRepository
				.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(), startDate, endDate);

		// List<ARCustomers> customers =
		// this.customersRepository.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(),
		// startDate, endDate);

		Map<DateTime, Long> counting = null;

		if (entities != null && !entities.isEmpty()) {
			if (ChartType.DAY.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARProspectCustomers::getCreatedDateChartKeyByDay, Collectors.counting()));
				dtos = counting.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
						.map(e -> new ChartDto(e.getKey(), e.getValue().floatValue())).collect(Collectors.toList());
			} else if (ChartType.MONTH.value().equals(type)) {
				counting = entities.stream().collect(Collectors
						.groupingBy(ARProspectCustomers::getCreatedDateChartKeyByMonth, Collectors.counting()));
				dtos = ChartUtil.buildChartByMonth(startDate, endDate, counting);
			} else {
				counting = entities.stream().collect(Collectors
						.groupingBy(ARProspectCustomers::getCreatedDateChartKeyByYear, Collectors.counting()));
				dtos = ChartUtil.buildChartByYear(startDate, endDate, counting);
			}
		}

		return dtos;
	}

	// Create prospect
	@Transactional
	public String changeProspectToCustomerContactERP(ChangeProspectDto dto, Integer employeeId, String token) {
		// Validate token
		if (!"24cb7e9b9b0d23462f6d8c24b3ca82ad".equals(token)) {
			throw new InvalidException("Token is invalid.", ErrorCodeEnum.NOT_PERMISSION);
		}

		return changeProspectToCustomerContact(dto, employeeId);
	}
}
