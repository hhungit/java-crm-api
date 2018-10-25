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

import com.bys.crm.app.dto.ChartDto;
import com.bys.crm.app.dto.OpportunityDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.app.mapping.OpportunityMapper;
import com.bys.crm.app.validation.OpportunityValidator;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ChartType;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomerResources;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.AROpportunityContactGroups;
import com.bys.crm.domain.erp.model.AROpportunitys;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.ARCampaignsRepository;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.AROpportunityContactGroupsRepository;
import com.bys.crm.domain.erp.repository.AROpportunitysRepository;
import com.bys.crm.domain.erp.repository.BRBranchsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.ChartUtil;
import com.bys.crm.util.DateTimeUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class OpportunityFacade {
	@Autowired
	private OpportunityMapper opportunityMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private AROpportunitysRepository opportunitysRepository;
	
	@Autowired
	private ARCampaignsRepository campaignsRepository;
	
	@Autowired
	private BRBranchsRepository branchsRepository;

	@Autowired
	private OpportunityValidator validator;

	@Autowired
	private ARCustomerContactRepository contactRepository;

	@Autowired
	private AROpportunityContactGroupsRepository groupRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	@Autowired
	private EmployeeService employeeService;
	
	 

	private static final Logger LOGGER = LoggerFactory.getLogger(OpportunityFacade.class);

	// Create opportunity
	@Transactional
	public OpportunityDto createOpportunity(OpportunityDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		// Convert data from dto to entity
		AROpportunitys entity = opportunityMapper.buildEntity(dto);

		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());

		// Get contacts from DTO
		if (dto.getContacts() != null && !dto.getContacts().isEmpty()) {
			// Convert contacts from ArrayList to Set
			entity.setOpportunityContactGroups(
					new HashSet<AROpportunityContactGroups>(getContactsAndInsert(dto, entity)));
		}

		// Save data into DB
		opportunitysRepository.save(entity);
		if (dto.getCampaign() != null && dto.getCampaign().getId() != null)  {
			ARCampaigns campaignRs = campaignsRepository.findByIdAndStatus(dto.getCampaign().getId(),  AAStatus.Alive.name());
			entity.setCampaign(campaignRs);
		}

		return opportunityMapper.buildDto(entity);
	}

	// Get opportunity by id
	public OpportunityDto getOpportunityById(Integer employeeId, Long opportunityId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		AROpportunitys opportunity = opportunitysRepository.findByIdAndStatus(opportunityId, AAStatus.Alive.name());

		if (opportunity == null) {
			throw new InvalidException("Opportunity is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return opportunityMapper.buildDto(opportunity, employeeId, groupIds);
	}

	// Edit opportunity
	@Transactional
	public OpportunityDto editOpportunity(OpportunityDto dto, Integer employeeId) {
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

		// Get opportunity
		AROpportunitys opportunity = opportunitysRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

		// Check Opportunity is exist or not
		if (opportunity == null) {
			throw new InvalidException("Opportunity is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Build entity
		opportunity.setName(dto.getName());
		if (dto.getCustomer() != null && dto.getCustomer().getId() != null) {
			ARCustomers customer = new ARCustomers();
			customer.setId(dto.getCustomer().getId());
			opportunity.setCustomer(customer);
		} else {
			opportunity.setCustomer(null);
		}
		// if (dto.getCustomerContact() != null && dto.getCustomerContact().getId() !=
		// null) {
		// ARCustomerContacts contact = new ARCustomerContacts();
		// contact.setId(dto.getCustomerContact().getId());
		// opportunity.setCustomerContact(contact);
		// } else {
		// opportunity.setCustomerContact(null);
		// }
		opportunity.setClassify(dto.getClassify());
		opportunity.setPotentialSources(dto.getPotentialSources());
		opportunity.setAmount(dto.getAmount());
		opportunity.setCompletionDate(dto.getCompletionDate());
		opportunity.setStep(dto.getStep());
		opportunity.setProbability(dto.getProbability());
		opportunity.setExpectedValue(dto.getExpectedValue());
		opportunity.setAssignedTo(dto.getAssignedTo());
		opportunity.setDescription(dto.getDescription());
		opportunity.setCustomerNm(dto.getCustomerNm());
		opportunity.setContactName(dto.getContactName());
		opportunity.setIsShare(dto.getIsShare());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees assignedTo = new HREmployees();
			assignedTo.setId(dto.getEmployee().getId());
			opportunity.setEmployee(assignedTo);
		} else {
			opportunity.setEmployee(null);
		}
		
		if (dto.getBranch() != null && dto.getBranch().getId() != null) {
			BRBranchs branchEdit = new BRBranchs();
			branchEdit.setId(dto.getBranch().getId());
			BRBranchs branh = branchsRepository.findByStatusAndId(AAStatus.Alive.name(), dto.getBranch().getId());
			branchEdit.setName(branh.getName());
			opportunity.setBranch(branchEdit);
		} else {
			opportunity.setBranch(null);
		}
		
		opportunity.setUpdatedDate(DateTime.now());
		opportunity.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			opportunity.setEmployeeGroup(group);
		} else {
			opportunity.setEmployeeGroup(null);
		}
		
		
		if (dto.getCampaign() != null && dto.getCampaign().getId() != null) {
			ARCampaigns campaign = new ARCampaigns();
			campaign.setId(dto.getCampaign().getId());
			ARCampaigns campaignRs = campaignsRepository.findByIdAndStatus(dto.getCampaign().getId(),  AAStatus.Alive.name());
			campaign.setName(campaignRs.getName());
			opportunity.setCampaign(campaign);
		} else {
			opportunity.setCampaign(null);
		}
		opportunity.setUpdatedUserId(employee.getId());

		if (dto.getCustomerResource() != null & dto.getCustomerResource().getId() != null) {
			ARCustomerResources customerResource = new ARCustomerResources();
			customerResource.setId(dto.getCustomerResource().getId());
			opportunity.setCustomerResource(customerResource);
		} else
			opportunity.setCustomerResource(null);

		// Get opportunityContactGroups by opportunity
		List<AROpportunityContactGroups> opportunityContactGroups = groupRepository
				.findByOpportunityAndOpportunityStatusAndStatus(opportunity, AAStatus.Alive.name(),
						AAStatus.Alive.name());

		if (dto.getContacts() != null && !dto.getContacts().isEmpty()) {
			// Get contacts and insert to ARCustomerContactGroups
			getContactsAndInsert(dto, opportunity);
		}

		// Delete opportunityContactGroups
		groupRepository.deleteInBatch(opportunityContactGroups);

		// Save data into DB
		opportunitysRepository.save(opportunity);
		if (dto.getBranch() != null && dto.getBranch().getId() != null) {
			
		}
		

		return opportunityMapper.buildDto(opportunity);
	}

	// Delete opportunity
	@Transactional
	public String deleteOpportunity(Long opportunityId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check opportunity is exist or not
		AROpportunitys opportunity = opportunitysRepository.findByIdAndStatus(opportunityId, AAStatus.Alive.name());
		if (opportunity == null) {
			throw new InvalidException("Opportunity is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		opportunity.setStatus(AAStatus.Delete.name());
		opportunity.setUpdatedDate(DateTime.now());
		opportunity.setUpdatedUser(employee.getName());
		opportunity.setUpdatedUserId(employee.getId());

		// Save data into DB
		opportunitysRepository.save(opportunity);

		return StatusMessengerEnum.Successful.name();
	}

	// Delete opportunity list
	@Transactional
	public String deleteOpportunityList(List<Long> idList, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (idList == null || idList.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		AROpportunitys opportunityEntity = null;

		// Loop id list
		for (Long id : idList) {
			// Get opportunity
			opportunityEntity = opportunitysRepository.findByIdAndStatus(id, AAStatus.Alive.name());

			// Check opportunity is exist or not
			if (opportunityEntity == null) {
				throw new InvalidException("Opportunity is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			opportunityEntity.setStatus(AAStatus.Delete.name());
			opportunityEntity.setUpdatedDate(DateTime.now());
			opportunityEntity.setUpdatedUser(employee.getName());
			opportunityEntity.setUpdatedUserId(employee.getId());

			// Save data into DB
			opportunitysRepository.save(opportunityEntity);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Search opportunity
	public PageableResult<OpportunityDto> searchOpportunity(Integer employeeId, String searchKey, Integer pageNumber,
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

//		// Get page request list with page number and page size.
//		Pageable opportunityPageRequest = new PageRequest(pageNumber, pageSize,
//				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "name")
//						: new Sort(Direction.fromStringOrNull(direct), sortBy));
//		String order = StringUtil.isEmpty(sortBy) ? "name ASC"
//				: sortBy + " " + Direction.fromStringOrNull(direct);

		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(employee.getId());
		Boolean isLeader = false;
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeader = employeeGroups.get(0).getIsLeader();
		}

		// Search opportunity by name, phone, email, website with searchKey
		List<AROpportunitys> opportunityList = opportunitysRepository
				.findByNameLikeOrCustomerNameLikeOrCustomerContactNameLike(searchKey, searchKey, searchKey, searchKey,
						AAStatus.Alive.name(), ListUtil.convertToArrayId(employee.getBranchs()), isLeader ? 1 : 0,
						employeeId, false);

//		// Convert the list of entity to the list of dto
//		List<OpportunityDto> dtos = null;
//		if (opportunityList != null && opportunityList.getContent() != null
//				&& !opportunityList.getContent().isEmpty()) {
//			dtos = opportunityList.getContent().stream().map(opportunity -> opportunityMapper.buildDto(opportunity))
//					.collect(Collectors.toList());
//		}
//
//		// Return PageableResult
//		return new PageableResult<>(pageNumber, opportunityList.getTotalPages(), opportunityList.getTotalElements(),
//				dtos);

		// Get size of opportunity list
		int listSize = opportunityList.size();

		// Get total page
		int totalPage = listSize / pageSize.intValue();
		if (listSize % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (listSize % pageSize.intValue()) > 0) {
			recordNumber = listSize % pageSize.intValue();
		}

		// Get result list
		List<OpportunityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(opportunityMapper.buildDto(opportunityList.get(i)));
			}
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(listSize), resultList);
	}

	// Search opportunity
	public List<OpportunityDto> searchOpportunityByName(Integer employeeId, String opportunityName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(opportunityName)) {
			try {
				opportunityName = ("%".concat(StringUtils.trim(URLDecoder.decode(opportunityName, "UTF-8")))
						.concat("%")).replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder opportunityName fail" + e);
			}
		} else {
			opportunityName = "%";
		}

		// Search opportunity by name
		List<AROpportunitys> opportunityList = opportunitysRepository
				.findByNameLikeAndStatusOrderByNameAsc(opportunityName, AAStatus.Alive.name());

		return opportunityMapper.buildDtos(opportunityList);
	}

	// Filter opportunity
	public PageableResult<OpportunityDto> opportunityFilter(Integer employeeId, String searchKey, String classify,
			String step, Integer pageNumber, Integer pageSize, String sortBy, String direct, Long fromDate,
			Long toDate) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		validator.validateFilter(classify, step);

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

		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(employee.getId());
		Boolean isLeader = false;
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeader = employeeGroups.get(0).getIsLeader();
		}
		 
		// Search opportunity with filter
		List<AROpportunitys> opportunityList = opportunitysRepository.findByClassifyLikeAndStepLikeAndStatus(searchKey,
				StringUtil.convertSearchKey(classify), StringUtil.convertSearchKey(step),
				fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
				toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
				ListUtil.convertToArrayId(employee.getBranchs()), isLeader ? 1 : 0, employeeId,
						ListUtil.convertToArrayId(groupIds),
						false);

		// Get size of opportunity list
		int listSize = opportunityList.size();

		// Get total page
		int totalPage = listSize / pageSize.intValue();
		if (listSize % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (listSize % pageSize.intValue()) > 0) {
			recordNumber = listSize % pageSize.intValue();
		}

		// Get result list
		List<OpportunityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(opportunityMapper.buildDto(opportunityList.get(i), employeeId, groupIds));
			}
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(listSize), resultList);
	}

	@Transactional
	public List<ChartDto> getOpportunityChart(Long employeeId, Long from, Long to, String type) {
		List<ChartDto> dtos = null;

		DateTime startDate = DateTimeUtil.toDateTimeAtStartOfDay(from);
		DateTime endDate = DateTimeUtil.toDateTimeAtEndOfDay(to);
		if (startDate.isAfter(endDate)) {
			throw new ResourceNotFoundException("End Date must be greater than Start Date.",
					ErrorCodeEnum.INVALID_DATE);
		}

		List<AROpportunitys> entities = this.opportunitysRepository
				.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(), startDate, endDate);

		Map<DateTime, Long> counting = null;

		if (entities != null && !entities.isEmpty()) {
			if (ChartType.DAY.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(AROpportunitys::getCreatedDateChartKeyByDay, Collectors.counting()));
				dtos = counting.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
						.map(e -> new ChartDto(e.getKey(), e.getValue().floatValue())).collect(Collectors.toList());
			} else if (ChartType.MONTH.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(AROpportunitys::getCreatedDateChartKeyByMonth, Collectors.counting()));
				dtos = ChartUtil.buildChartByMonth(startDate, endDate, counting);
			} else {
				counting = entities.stream().collect(
						Collectors.groupingBy(AROpportunitys::getCreatedDateChartKeyByYear, Collectors.counting()));
				dtos = ChartUtil.buildChartByYear(startDate, endDate, counting);
			}
		}

		return dtos;
	}

	// Get opportunities by customer id
	public PageableResult<OpportunityDto> getOpportunitiesByCustomerId(Integer employeeId, Long customerId,
			Integer pageNumber, Integer pageSize, String sortBy, String direct) {
		// Validate employee id
		if (employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId) == null) {
			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get page request list with page number and page size.
		Pageable pageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "name")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		// Get opportunities by customer id
		Page<AROpportunitys> opportunityList = opportunitysRepository.findByCustomerIdAndStatus(customerId,
				AAStatus.Alive.name(), pageRequest);

		// Convert the list of entity to the list of dto
		List<OpportunityDto> dtos = null;
		if (opportunityList != null && opportunityList.getContent() != null
				&& !opportunityList.getContent().isEmpty()) {
			dtos = opportunityList.getContent().stream().map(opportunity -> opportunityMapper.buildDto(opportunity))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, opportunityList.getTotalPages(), opportunityList.getTotalElements(),
				dtos);
	}

	// Get opportunities by contact id
	public PageableResult<OpportunityDto> getOpportunitiesByContacId(Integer employeeId, Long contactId,
			Integer pageNumber, Integer pageSize, String sortBy, String direct) {
		// Validate employee id
		if (employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId) == null) {
			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// // Get page request list with page number and page size.
		// Pageable pageRequest = new PageRequest(pageNumber, pageSize,
		// StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "name")
		// : new Sort(Direction.fromStringOrNull(direct), sortBy));

		// Get opportunities by contact id
		// Page<AROpportunitys> opportunityList =
		// opportunitysRepository.findByCustomerContactIdAndStatus(contactId,
		// AAStatus.Alive.name(), pageRequest);
		ARCustomerContacts contact = new ARCustomerContacts();
		contact.setId(contactId);
		List<AROpportunityContactGroups> opportunityContactGroups = groupRepository
				.findByContactAndContactStatusAndStatus(contact, AAStatus.Alive.name(), AAStatus.Alive.name());

		// Convert the list of entity to the list of dto
		List<OpportunityDto> dtos = null;
		if (opportunityContactGroups != null && !opportunityContactGroups.isEmpty()) {
			dtos = opportunityContactGroups.stream().map(group -> opportunityMapper.buildDto(group.getOpportunity()))
					.collect(Collectors.toList());
		}

		int listSize = dtos == null ? 0 : dtos.size();
		// Get total page
		int totalPage = listSize / pageSize.intValue();
		if (listSize % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (listSize % pageSize.intValue()) > 0) {
			recordNumber = listSize % pageSize.intValue();
		}

		// Get result list
		List<OpportunityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(dtos.get(i));
			}
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(listSize), resultList);
	}

	// Get contacts and insert to AROpportunityContactGroups
	public List<AROpportunityContactGroups> getContactsAndInsert(OpportunityDto dto, AROpportunitys opportunity) {
		List<AROpportunityContactGroups> opportunityContactGroups = new ArrayList<>();

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

			// Insert data to AROpportunityContactGroups table
			AROpportunityContactGroups opportunityContactGroup;
			AtomicLong maxId = keyGenerationService.findMaxId(groupRepository);
			for (ARCustomerContacts contact : contacts) {
				opportunityContactGroup = new AROpportunityContactGroups();
				opportunityContactGroup.setOpportunity(opportunity);
				opportunityContactGroup.setStatus(AAStatus.Alive.name());
				opportunityContactGroup.setContact(contact);
				opportunityContactGroup.setId(maxId.incrementAndGet());
				opportunityContactGroups.add(opportunityContactGroup);
			}
			;

			groupRepository.save(opportunityContactGroups);
		}
		return opportunityContactGroups;
	}
}
