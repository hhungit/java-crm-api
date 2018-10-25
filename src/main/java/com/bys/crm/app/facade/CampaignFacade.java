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

import com.bys.crm.app.dto.CampaignDto;
import com.bys.crm.app.dto.ChartDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.app.validation.CampaignValidator;
import com.bys.crm.app.mapping.CampaignMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ChartType;
import com.bys.crm.domain.erp.constant.NotificationType;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.ARCampaignsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.util.ChartUtil;
import com.bys.crm.util.DateTimeUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class CampaignFacade {
	@Autowired
	private CampaignMapper campaignMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARCampaignsRepository campaignsRepository;

	@Autowired
	private CampaignValidator validator;

	@Autowired
	private NotificationFacade notificationFacade;

	private static final Logger LOGGER = LoggerFactory.getLogger(CampaignFacade.class);

	// Create campaign
	@Transactional
	public CampaignDto createCampaign(CampaignDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		// Convert data from dto to entity
		ARCampaigns entity = campaignMapper.buildEntity(dto);

		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());

		// Save data into DB
		campaignsRepository.save(entity);

		// Build entity
		notificationFacade.buildNotification(entity.getEmployee(), entity.getBranch(), NotificationType.Campaign.name(),
				entity.getId(), entity.getName(), entity.getStartDate(), entity.getCompletionDate(), null,
				entity.getEmployeeGroup());

		return campaignMapper.buildDto(entity);
	}

	// Get campaign by id
	public CampaignDto getCampaignById(Integer employeeId, Long campaignId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		ARCampaigns campaign = campaignsRepository.findByIdAndStatus(campaignId, AAStatus.Alive.name());

		if (campaign == null) {
			throw new InvalidException("Campaign is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return campaignMapper.buildDto(campaign, employeeId, groupIds);
	}

	// Edit campaign
	@Transactional
	public CampaignDto editCampaign(CampaignDto dto, Integer employeeId) {
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

		// Get campaign
		ARCampaigns campaign = campaignsRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

		// Check Campaign is exist or not
		if (campaign == null) {
			throw new InvalidException("Campaign is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Build entity
		campaign.setName(dto.getName());
		campaign.setCampaignStatus(dto.getCampaignStatus());
		campaign.setType(dto.getType());
		campaign.setCampaignObject(dto.getCampaignObject());
		campaign.setAssignedTo(dto.getAssignedTo());
		campaign.setStartDate(dto.getStartDate());
		campaign.setCompletionDate(dto.getCompletionDate());
		campaign.setDonor(dto.getDonor());
		campaign.setGoals(dto.getGoals());
		campaign.setExpectedNumber(dto.getExpectedNumber());
		campaign.setBudget(dto.getBudget());
		campaign.setCosts(dto.getCosts());
		campaign.setExpectedRevenue(dto.getExpectedRevenue());
		campaign.setActualRevenue(dto.getActualRevenue());
		campaign.setExpectedResults(dto.getExpectedResults());
		campaign.setActualResults(dto.getActualResults());
		campaign.setDescription(dto.getDescription());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees assignedTo = new HREmployees();
			assignedTo.setId(dto.getEmployee().getId());
			campaign.setEmployee(assignedTo);
		} else {
			campaign.setEmployee(null);
		}
		campaign.setUpdatedDate(DateTime.now());
		campaign.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			campaign.setEmployeeGroup(group);
		} else {
			campaign.setEmployeeGroup(null);
		}
		campaign.setUpdatedUserId(employee.getId());

		// Save data into DB
		campaignsRepository.save(campaign);

		// Update notification
		notificationFacade.updateNotification(NotificationType.Campaign.name(), campaign.getId(), campaign.getName(),
				campaign.getEmployee(), campaign.getEmployeeGroup(), campaign.getBranch(), campaign.getStartDate(),
				campaign.getCompletionDate(), null);

		return campaignMapper.buildDto(campaign);
	}

	// Delete campaign
	@Transactional
	public String deleteCampaign(Long campaignId, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Check campaign is exist or not
		ARCampaigns campaign = campaignsRepository.findByIdAndStatus(campaignId, AAStatus.Alive.name());
		if (campaign == null) {
			throw new InvalidException("Campaign is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Update status to delete
		campaign.setStatus(AAStatus.Delete.name());
		campaign.setUpdatedDate(DateTime.now());
		campaign.setUpdatedUser(employee.getName());
		campaign.setUpdatedUserId(employee.getId());

		// Save data into DB
		campaignsRepository.save(campaign);

		// Delete notification
		notificationFacade.deleteNotification(NotificationType.Campaign.name(), campaignId);

		return StatusMessengerEnum.Successful.name();
	}

	// Delete campaign list
	@Transactional
	public String deleteCampaignList(List<Long> idList, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (idList == null || idList.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARCampaigns campaignEntity = null;

		// Loop id list
		for (Long id : idList) {
			// Get campaign
			campaignEntity = campaignsRepository.findByIdAndStatus(id, AAStatus.Alive.name());

			// Check campaign is exist or not
			if (campaignEntity == null) {
				throw new InvalidException("Campaign is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			campaignEntity.setStatus(AAStatus.Delete.name());
			campaignEntity.setUpdatedDate(DateTime.now());
			campaignEntity.setUpdatedUser(employee.getName());
			campaignEntity.setUpdatedUserId(employee.getId());

			// Save data into DB
			campaignsRepository.save(campaignEntity);

			// Delete notification
			notificationFacade.deleteNotification(NotificationType.Campaign.name(), id);
		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Search campaign
	public PageableResult<CampaignDto> searchCampaign(Integer employeeId, String searchKey, Integer pageNumber,
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
		Pageable campaignPageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "campaignNo")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));
		// Search campaign by name, phone, email, website with searchKey
		Page<ARCampaigns> campaignList = campaignsRepository.findByNameLikeOrEmployeeNameLike(searchKey, searchKey,
				AAStatus.Alive.name(), campaignPageRequest);

		// Convert the list of entity to the list of dto
		List<CampaignDto> dtos = null;
		if (campaignList != null && campaignList.getContent() != null && !campaignList.getContent().isEmpty()) {
			dtos = campaignList.getContent().stream().map(campaign -> campaignMapper.buildDto(campaign, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, campaignList.getTotalPages(), campaignList.getTotalElements(), dtos);
	}

	// Search campaign
	public List<CampaignDto> searchCampaignByName(Integer employeeId, String campaignName) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(campaignName)) {
			try {
				campaignName = ("%".concat(StringUtils.trim(URLDecoder.decode(campaignName, "UTF-8"))).concat("%"))
						.replace(" ", "%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder campaignName fail" + e);
			}
		} else {
			campaignName = "%";
		}
		
		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Search campaign by name
		List<ARCampaigns> campaignList = campaignsRepository.findByNameLikeAndStatusOrderByNameAsc(campaignName,
				AAStatus.Alive.name());
		
		List<CampaignDto> dtos = null;
		if (campaignList != null    && !campaignList.isEmpty()) {
			dtos = campaignList.stream().map(campaign -> campaignMapper.buildDto(campaign, employeeId, groupIds)).collect(Collectors.toList());
					 
		}

		return  dtos;//campaignMapper.buildDtos(campaignList);
	}

	// Filter campaign
	public PageableResult<CampaignDto> campaignFilter(Integer employeeId, String searchKey, String type,
			String campaignStatus, Integer pageNumber, Integer pageSize, String sortBy, String direct, Long fromDate,
			Long toDate) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		validator.validateFilter(type, campaignStatus);

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

		// Search campaign with filter
		Page<ARCampaigns> campaignList = campaignsRepository.findByTypeLikeAndCampaignStatusLikeAndStatus(searchKey,
				StringUtil.convertSearchKey(type), StringUtil.convertSearchKey(campaignStatus),
				fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
				toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
				pageRequest);

		// Convert the list of entity to the list of dto
		List<CampaignDto> dtos = null;
		if (campaignList != null && campaignList.getContent() != null && !campaignList.getContent().isEmpty()) {
			dtos = campaignList.getContent().stream()
					.map(campaign -> campaignMapper.buildDto(campaign, employeeId, groupIds))
					.collect(Collectors.toList());
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, campaignList.getTotalPages(), campaignList.getTotalElements(), dtos);
	}

	@Transactional
	public List<ChartDto> getCampaignChart(Long employeeId, Long from, Long to, String type) {
		List<ChartDto> dtos = null;

		DateTime startDate = DateTimeUtil.toDateTimeAtStartOfDay(from);
		DateTime endDate = DateTimeUtil.toDateTimeAtEndOfDay(to);
		if (startDate.isAfter(endDate)) {
			throw new ResourceNotFoundException("End Date must be greater than Start Date.",
					ErrorCodeEnum.INVALID_DATE);
		}

		List<ARCampaigns> entities = this.campaignsRepository
				.findByStatusAndCreatedDateBetweenOrderByCreatedDate(AAStatus.Alive.name(), startDate, endDate);

		Map<DateTime, Long> counting = null;

		if (entities != null && !entities.isEmpty()) {
			if (ChartType.DAY.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCampaigns::getCreatedDateChartKeyByDay, Collectors.counting()));
				dtos = counting.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
						.map(e -> new ChartDto(e.getKey(), e.getValue().floatValue())).collect(Collectors.toList());
			} else if (ChartType.MONTH.value().equals(type)) {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCampaigns::getCreatedDateChartKeyByMonth, Collectors.counting()));
				dtos = ChartUtil.buildChartByMonth(startDate, endDate, counting);
			} else {
				counting = entities.stream().collect(
						Collectors.groupingBy(ARCampaigns::getCreatedDateChartKeyByYear, Collectors.counting()));
				dtos = ChartUtil.buildChartByYear(startDate, endDate, counting);
			}
		}

		return dtos;
	}
}
