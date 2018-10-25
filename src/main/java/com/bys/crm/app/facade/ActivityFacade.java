package com.bys.crm.app.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

import com.bys.crm.app.dto.ActivityDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.GroupSummaryDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.validation.ActivityValidator;
import com.bys.crm.app.mapping.ActivityMapper;
import com.bys.crm.app.mapping.TaskMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.constant.ActivityObjectType;
import com.bys.crm.domain.erp.constant.ActivityType;
import com.bys.crm.domain.erp.model.ARActivitys;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.model.HREmployeeGroups;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.model.PMTaskAssigns;
import com.bys.crm.domain.erp.model.PMTasks;
import com.bys.crm.domain.erp.repository.ARActivitysRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.repository.PMTaskAssignsRepository;
import com.bys.crm.domain.erp.repository.PMTasksRepository;
import com.bys.crm.domain.erp.service.EmployeeService;
import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.FileUtil;
import com.bys.crm.util.ListUtil;
import com.bys.crm.util.StringUtil;

@Service
public class ActivityFacade {
	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARActivitysRepository activitysRepository;

	@Autowired
	private ActivityValidator validator;

	@Autowired
	private NotificationFacade notificationFacade;

	@Autowired
	private PMTasksRepository tasksRepository;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	protected KeyGenerationService keyGenerationService;

	@Autowired
	private PMTaskAssignsRepository taskAssignsRepository;

	@Autowired
	private EmployeeService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityFacade.class);

	// Create activity
	@Transactional
	public ActivityDto createActivity(ActivityDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		validator.validate(dto);

		if (ActivityType.Event.name().equalsIgnoreCase(dto.getActivityType())) {
			return handleCreateEvent(dto, employee);
		} else {
			return handleCreateWork(dto, employee);
		}

	}

	// Get activity by id
	public ActivityDto getActivityById(Integer employeeId, Long activityId, String activityType) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		ARActivitys event = null;
		PMTasks work = null;
		if (ActivityType.Event.name().equalsIgnoreCase(activityType)) {
			event = activitysRepository.findByIdAndStatus(activityId, AAStatus.Alive.name());
			if (event == null) {
				throw new InvalidException("Activity is not exist.", ErrorCodeEnum.INVALID_REQUEST);
			}
			return activityMapper.buildDto(event, employeeId, groupIds);
		} else {
			work = tasksRepository.findByIdAndStatus(activityId, AAStatus.Alive.name());
			if (work == null) {
				throw new InvalidException("Activity is not exist.", ErrorCodeEnum.INVALID_REQUEST);
			}
			return taskMapper.buildDto(work, employeeId, groupIds);
		}
	}

	// Edit activity
	@Transactional
	public ActivityDto editActivity(ActivityDto dto, Integer employeeId, String activitiType) {
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

		if (ActivityType.Event.name().equalsIgnoreCase(activitiType)) {
			return handleEditEvent(dto, employee);
		} else {
			return handleEditWork(dto, employee);
		}
	}

	// Delete activity
	@Transactional
	public String deleteActivity(Long activityId, Integer employeeId, String activityType) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (ActivityType.Event.name().equalsIgnoreCase(activityType)) {
			// Check activity is exist or not
			ARActivitys activity = activitysRepository.findByIdAndStatus(activityId, AAStatus.Alive.name());
			if (activity == null) {
				throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			activity.setStatus(AAStatus.Delete.name());
			activity.setUpdatedDate(DateTime.now());
			activity.setUpdatedUser(employee.getName());
			activity.setUpdatedUserId(employee.getId());

			// Save data into DB
			activitysRepository.save(activity);

			// Delete notification
			notificationFacade.deleteNotification(ActivityType.Event.name(), activityId);

			return StatusMessengerEnum.Successful.name();
		} else {
			// Check activity is exist or not
			PMTasks work = tasksRepository.findByIdAndStatus(activityId, AAStatus.Alive.name());
			if (work == null) {
				throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
			}

			// Update status to delete
			work.setStatus(AAStatus.Delete.name());
			work.setUpdatedDate(DateTime.now());
			work.setUpdatedUser(employee.getName());
			work.setUpdatedUserId(employee.getId());

			// Save data into DB
			tasksRepository.save(work);

			// Delete notification
			notificationFacade.deleteNotification(ActivityType.Work.name(), activityId);

			return StatusMessengerEnum.Successful.name();
		}

	}

	// Delete activity list
	@Transactional
	public String deleteActivityList(List<ActivityDto> dtos, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (dtos == null || dtos.isEmpty()) {
			throw new InvalidException("Id is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		ARActivitys eventEntity = null;
		PMTasks taskEntity = null;

		// Loop id list
		for (ActivityDto dto : dtos) {
			if (ActivityType.Event.name().equalsIgnoreCase(dto.getActivityType())) {
				// Get event
				eventEntity = activitysRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

				// Check activity is exist or not
				if (eventEntity == null) {
					throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
				}

				// Update status to delete
				eventEntity.setStatus(AAStatus.Delete.name());
				eventEntity.setUpdatedDate(DateTime.now());
				eventEntity.setUpdatedUser(employee.getName());
				eventEntity.setUpdatedUserId(employee.getId());

				// Save data into DB
				activitysRepository.save(eventEntity);

				// Delete notification
				notificationFacade.deleteNotification(ActivityType.Event.name(), dto.getId());
			} else {
				// Get work
				taskEntity = tasksRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

				// Check activity is exist or not
				if (taskEntity == null) {
					throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
				}

				// Update status to delete
				taskEntity.setStatus(AAStatus.Delete.name());
				taskEntity.setUpdatedDate(DateTime.now());
				taskEntity.setUpdatedUser(employee.getName());
				taskEntity.setUpdatedUserId(employee.getId());

				// Save data into DB
				tasksRepository.save(taskEntity);

				// Delete notification
				notificationFacade.deleteNotification(ActivityType.Work.name(), dto.getId());
			}

		}

		// return success message
		return StatusMessengerEnum.Successful.name();
	}

	// Search activity
	public PageableResult<ActivityDto> searchActivity(Integer employeeId, String searchKey, Integer pageNumber,
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

		List<HREmployeeGroups> employeeGroups = employeeService.getEmployeeGroups(employee.getId());
		Boolean isLeader = false;
		if (employeeGroups != null && !employeeGroups.isEmpty()) {
			isLeader = employeeGroups.get(0).getIsLeader();
		}

		// Search activity by name, phone, email, website with searchKey
		List<ARActivitys> events = activitysRepository.findByNameLikeOrAddressLikeOrEmployeeNameLike(searchKey,
				searchKey, searchKey, AAStatus.Alive.name(), ListUtil.convertToArrayId(employee.getBranchs()),
				isLeader ? 1 : 0, employeeId, false);

		// Search activity by name, phone, email, website with searchKey
		List<PMTasks> works = tasksRepository.findByNameLikeOrAddressLikeOrEmployeeNameLike(searchKey, searchKey,
				searchKey, AAStatus.Alive.name(), "CRM", ListUtil.convertToArrayId(employee.getBranchs()),
				isLeader ? 1 : 0, employeeId, false);

		// Convert the list of entity to the list of dto
		List<ActivityDto> dtos = events.stream().map(activity -> activityMapper.buildDto(activity))
				.collect(Collectors.toList());

		dtos.addAll(works.stream().map(work -> taskMapper.buildDto(work)).collect(Collectors.toList()));

		// Sort by start date DESC
		Collections.sort(dtos);

		// Get total page
		int totalPage = dtos.size() / pageSize.intValue();
		if (dtos.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (dtos.size() % pageSize.intValue()) > 0) {
			recordNumber = dtos.size() % pageSize.intValue();
		}

		// Get result list
		List<ActivityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(dtos.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(dtos.size()), resultList);
	}

	// Import activity from excel file
	@Transactional
	public String importActivityFromExcel(Integer employeeId, MultipartFile file) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from excel file to activity dto
		List<ActivityDto> activityDtos = FileUtil.convertExcelToObject(FileUtil.getUploadPath(file).toString(),
				ActivityDto.class, ADObjectType.Activity.name());

		ARActivitys entity = null;
		List<HREmployees> employees = null;
		for (ActivityDto dto : activityDtos) {
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

			entity = activityMapper.buildEntity(dto);
			entity.setCreatedDate(DateTime.now());
			entity.setCreatedUser(employee.getName());
			entity.setUpdatedDate(DateTime.now());
			entity.setUpdatedUser(employee.getName());
			entity.setCreatedUserId(employee.getId());
			entity.setUpdatedUserId(employee.getId());

			// save data into DB
			activitysRepository.save(entity);

			// Build notification
			notificationFacade.buildNotification(entity.getEmployee(), entity.getBranch(), entity.getActivityType(),
					entity.getId(), entity.getName(), entity.getStartDate(), entity.getEndDate(), null,
					entity.getEmployeeGroup());
		}

		return StatusMessengerEnum.Successful.name();
	}

	// Get activity by Employee
	public List<ActivityDto> getActivityByEmployee(Integer employeeId, String activityType) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (ActivityType.fromValue(activityType) == null) {
			throw new InvalidException("Valid ActivityObjectType values is " + ActivityObjectType.supportValues(),
					ErrorCodeEnum.INVALID_ACTIVITY_TYPE);
		}

		// Get activities
		List<ARActivitys> activityList = activitysRepository
				.findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatusOrderByStartDate(
						ActivityType.fromValue(activityType).name(), employeeId, DateTime.now(), AAStatus.Alive.name());

		return activityMapper.buildDtos(activityList);
	}

	// Get activity by Object Type
	public PageableResult<ActivityDto> getActivityByObjectType(Integer employeeId, String objectType, Long objectId,
			Integer pageNumber, Integer pageSize, String sortBy, String direct) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

//		// Get page request list with page number and page size.
//		Pageable activityPageRequest = new PageRequest(pageNumber, pageSize,
//				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "startDate")
//						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		// Get events
		List<ARActivitys> events = activitysRepository
				.findByActivityObjectTypeAndActivityObjectTypeIdAndStatus(objectType, objectId, AAStatus.Alive.name());

		// Get works
		List<PMTasks> works = tasksRepository.findByActivityObjectTypeAndActivityObjectTypeIdAndStatusAndTaskType(
				objectType, objectId, AAStatus.Alive.name(), "CRM");

		// Convert the list of entity to the list of dto
		List<ActivityDto> dtos = events.stream().map(event -> activityMapper.buildDto(event))
				.collect(Collectors.toList());

		// Convert the list of entity to the list of dto
		dtos.addAll(works.stream().map(work -> taskMapper.buildDto(work)).collect(Collectors.toList()));

		// Sort by start date DESC
		Collections.sort(dtos);

		// Get total page
		int totalPage = dtos.size() / pageSize.intValue();
		if (dtos.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (dtos.size() % pageSize.intValue()) > 0) {
			recordNumber = dtos.size() % pageSize.intValue();
		}

		// Get result list
		List<ActivityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(dtos.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(dtos.size()), resultList);
	}

	// Get activity by Employee and Type
	public PageableResult<ActivityDto> getActivityByEmployeeAndType(Integer employeeId, String activityType,
			Integer pageNumber, Integer pageSize, String sortBy, String direct) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group list
		Set<HRGroups> groups = employee.getGroups();

		// Get group id list
		ArrayList<Integer> groupIdList = new ArrayList<>();
		groups.forEach(group -> {
			groupIdList.add(group.getId());
		});
		Integer[] groupIdArray = groupIdList.isEmpty() ? null : groupIdList.toArray(new Integer[0]);

		// Get page request list with page number and page size.
		Pageable activityPageRequest = new PageRequest(pageNumber, pageSize,
				StringUtil.isEmpty(sortBy) ? new Sort(Direction.ASC, "startDate")
						: new Sort(Direction.fromStringOrNull(direct), sortBy));

		PageableResult<ActivityDto> page = null;
		List<ActivityDto> dtos = new ArrayList<>();

		if (ActivityType.Work.name().equalsIgnoreCase(activityType)) {
//				Page<PMTasks> taskEntities = tasksRepository
//						.findByStatusAndStartDateGreaterThanEqualAndTaskAssignsEmployeeId(
//								AAStatus.Alive.name(), DateTime.now(), employeeId, activityPageRequest);
			Long tess = DateTime.now(DateTimeZone.forID("Asia/Ho_Chi_Minh")).getMillis();
			Page<PMTasks> taskEntities = tasksRepository
					.findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatus(employeeId, groupIdArray,
							DateTime.now(), AAStatus.Alive.name(), activityPageRequest);

			// Convert the list of entity to the list of dto
			if (taskEntities != null && taskEntities.getContent() != null && !taskEntities.getContent().isEmpty()) {
				dtos = taskEntities.getContent().stream().map(taskEntity -> taskMapper.buildDto(taskEntity))
						.collect(Collectors.toList());
			}

			page = new PageableResult<>(pageNumber, taskEntities.getTotalPages(), taskEntities.getTotalElements(),
					dtos);
		} else {
			// Get activities
			Page<ARActivitys> activityList = activitysRepository
					.findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatus(
							ActivityType.fromValue(activityType).name(), employeeId, groupIdArray, DateTime.now(),
							AAStatus.Alive.name(), activityPageRequest);

			// Convert the list of entity to the list of dto
			if (activityList != null && activityList.getContent() != null && !activityList.getContent().isEmpty()) {
				dtos = activityList.getContent().stream().map(activity -> activityMapper.buildDto(activity))
						.collect(Collectors.toList());
			}

			page = new PageableResult<>(pageNumber, activityList.getTotalPages(), activityList.getTotalElements(),
					dtos);
		}

		// Return PageableResult
		return page;
	}

	// Filter activity
	public PageableResult<ActivityDto> activityFilter(Integer employeeId, String searchKey, String activityType,
			String activityStatus, Long fromDate, Long toDate, Integer pageNumber, Integer pageSize, String sortBy,
			String direct, Integer createdUserId, Integer assignmentEmployeeId, Integer assignmentEmployeeGroupId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIds = ListUtil.getGroupIds(employee.getGroups());

		// Validate filter value
		validator.validateFilter(activityType, activityStatus);

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

		// Search activity with filter
		List<ARActivitys> events = activitysRepository.findByActivityTypeLikeAndActivityStatusLikeAndStatus(searchKey,
				StringUtil.convertSearchKey(activityStatus),
				fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
				toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(),
				ListUtil.convertToArrayId(employee.getBranchs()), isLeader ? 1 : 0, employeeId, false,
				StringUtil.convertSearchKey(createdUserId),
				StringUtil.convertSearchKey(assignmentEmployeeId),
				StringUtil.convertSearchKey(assignmentEmployeeGroupId));

		// Search activity with filter
		List<PMTasks> works = tasksRepository.findByActivityTypeLikeAndActivityStatusLikeAndStatus2(searchKey,
				StringUtil.convertSearchKey(activityStatus),
				fromDate == null ? new DateTime(-2211753600000L) : new DateTime(fromDate),
				toDate == null ? new DateTime(4068144000000L) : new DateTime(toDate), AAStatus.Alive.name(), "CRM",
				ListUtil.convertToArrayId(employee.getBranchs()), isLeader ? 1 : 0, employeeId, false,
				StringUtil.convertSearchKey(createdUserId),
				StringUtil.convertSearchKey(assignmentEmployeeId),
				StringUtil.convertSearchKey(assignmentEmployeeGroupId) 
				);

		List<ActivityDto> dtos = new ArrayList<>();

		if (activityType == null) {
			// Convert the list of entity to the list of dto
			dtos.addAll(events.stream().map(event -> activityMapper.buildDto(event, employeeId, groupIds))
					.collect(Collectors.toList()));

			// Convert the list of entity to the list of dto
			dtos.addAll(works.stream().map(work -> taskMapper.buildDto(work, employeeId, groupIds))
					.collect(Collectors.toList()));
		} else if (ActivityType.Event.name().equalsIgnoreCase(activityType)) {
			// Convert the list of entity to the list of dto
			dtos.addAll(events.stream().map(event -> activityMapper.buildDto(event, employeeId, groupIds))
					.collect(Collectors.toList()));
		} else {
			// Convert the list of entity to the list of dto
			dtos.addAll(works.stream().map(work -> taskMapper.buildDto(work, employeeId, groupIds))
					.collect(Collectors.toList()));
		}

		// Sort by start date DESC
		Collections.sort(dtos);

		// Get total page
		int totalPage = dtos.size() / pageSize.intValue();
		if (dtos.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (dtos.size() % pageSize.intValue()) > 0) {
			recordNumber = dtos.size() % pageSize.intValue();
		}

		// Get result list
		List<ActivityDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(dtos.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(dtos.size()), resultList);
	}

	@Transactional
	private ActivityDto handleCreateEvent(ActivityDto dto, HREmployees employee) {

		// Convert data from dto to entity
		ARActivitys entity = activityMapper.buildEntity(dto);

		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());

		// Save data into DB
		activitysRepository.save(entity);

		// Build entity
		notificationFacade.buildNotification(entity.getEmployee(), entity.getBranch(), entity.getActivityType(),
				entity.getId(), entity.getName(), entity.getStartDate(), entity.getEndDate(), null,
				entity.getEmployeeGroup());

		return activityMapper.buildDto(entity);
	}

	@Transactional
	private ActivityDto handleCreateWork(ActivityDto dto, HREmployees employee) {

		// Convert data from dto to entity
		PMTasks entity = taskMapper.buildEntity(dto);

		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());
		entity.setCreatedUser(employee.getName());
		entity.setUpdatedUser(employee.getName());
		entity.setCreatedUserId(employee.getId());
		entity.setUpdatedUserId(employee.getId());
		entity.setTaskType("CRM");

		// Save data into DB
		tasksRepository.save(entity);

		// Build notification
		entity.getTaskAssigns().forEach(taskAssign -> {
			notificationFacade.buildNotification(taskAssign.getEmployee(), entity.getBranch(), ActivityType.Work.name(),
					entity.getId(), entity.getName(), entity.getStartDate(), entity.getEndDate(), null,
					taskAssign.getEmployeeGroup());
		});

		return taskMapper.buildDto(entity);
	}

	@Transactional
	private ActivityDto handleEditEvent(ActivityDto dto, HREmployees employee) {
		// Get activity
		ARActivitys activity = activitysRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

		// Check Activity is exist or not
		if (activity == null) {
			throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Build entity
		activity.setName(dto.getName());
		activity.setAssignedTo(dto.getAssignedTo());
		activity.setStartDate(dto.getStartDate());
		activity.setEndDate(dto.getEndDate());
		activity.setActivityStatus(dto.getActivityStatus());
		activity.setEventType(dto.getEventType());
		activity.setAddress(dto.getAddress());
		activity.setActivityType(dto.getActivityType());
		activity.setActivityObjectTypeId(dto.getActivityObjectTypeId());
		activity.setActivityObjectTypeName(dto.getActivityObjectTypeName());
		activity.setDescription(dto.getDescription());
		activity.setActivityObjectType(dto.getActivityObjectType());
		activity.setIsShare(dto.getIsShare());
		if (dto.getEmployee() != null && dto.getEmployee().getId() != null) {
			HREmployees assignedTo = new HREmployees();
			assignedTo.setId(dto.getEmployee().getId());
			activity.setEmployee(assignedTo);
		} else {
			activity.setEmployee(null);
		}
		activity.setUpdatedDate(DateTime.now());
		activity.setUpdatedUser(employee.getName());
		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() != null) {
			HRGroups group = new HRGroups();
			group.setId(dto.getEmployeeGroup().getId());
			activity.setEmployeeGroup(group);
		} else {
			activity.setEmployeeGroup(null);
		}
		if (dto.getBranch() != null && dto.getBranch().getId() != null) {
			BRBranchs branch = new BRBranchs();
			branch.setId(dto.getBranch().getId());
			activity.setBranch(branch);
		}
		activity.setUpdatedUserId(employee.getId());

		// Save data into DB
		activitysRepository.save(activity);

		// Update notification
		notificationFacade.updateNotification(ActivityType.Event.name(), activity.getId(), activity.getName(),
				activity.getEmployee(), activity.getEmployeeGroup(), activity.getBranch(), activity.getStartDate(),
				activity.getEndDate(), activity.getAddress());

		return activityMapper.buildDto(activity);
	}

	@Transactional
	private ActivityDto handleEditWork(ActivityDto dto, HREmployees employee) {
		// Get activity
		PMTasks task = tasksRepository.findByIdAndStatus(dto.getId(), AAStatus.Alive.name());

		// Check Activity is exist or not
		if (task == null) {
			throw new InvalidException("Activity is not exist", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<PMTaskAssigns> oldTaskAssigns = task.getTaskAssigns();

		// Build entity
		task.setName(dto.getName());
		task.setStartDate(dto.getStartDate());
		task.setEndDate(dto.getEndDate());
		task.setActivityStatus(dto.getActivityStatus());
		task.setAddress(dto.getAddress());
		task.setActivityObjectTypeId(dto.getActivityObjectTypeId());
		task.setActivityObjectTypeName(dto.getActivityObjectTypeName());
		task.setDescription(dto.getDescription());
		task.setActivityObjectType(dto.getActivityObjectType());
		task.setUpdatedDate(DateTime.now());
		task.setUpdatedUser(employee.getName());
		task.setUpdatedUserId(employee.getId());
		task.setIsShare(dto.getIsShare());

		if (dto.getBranch() != null && dto.getBranch().getId() != null) {
			BRBranchs branch = new BRBranchs();
			branch.setId(dto.getBranch().getId());
			task.setBranch(branch);
		}

		PMTaskAssigns taskAssign = null;
		HRGroups group = null;
		Long newId = null;
		List<PMTaskAssigns> taskAssigns = new ArrayList<>();
		AtomicLong maxId = keyGenerationService.findMaxId(taskAssignsRepository);
		if (dto.getEmployees() != null && !dto.getEmployees().isEmpty()) {
			for (EmployeeSummaryDto employeeDto : dto.getEmployees()) {
				employee = new HREmployees();
				employee.setId(employeeDto.getId());

				taskAssign = new PMTaskAssigns();
				taskAssign.setTask(task);
				taskAssign.setEmployee(employee);
				newId = maxId.incrementAndGet();
				maxId = new AtomicLong(newId);
				taskAssign.setId(newId);
				taskAssigns.add(taskAssign);
			}
		} else if (dto.getEmployeeGroups() != null && !dto.getEmployeeGroups().isEmpty()) {
			for (GroupSummaryDto groupDto : dto.getEmployeeGroups()) {
				group = new HRGroups();
				group.setId(groupDto.getId());

				taskAssign = new PMTaskAssigns();
				taskAssign.setTask(task);
				taskAssign.setEmployeeGroup(group);
				newId = maxId.incrementAndGet();
				maxId = new AtomicLong(newId);
				taskAssign.setId(newId);
				taskAssigns.add(taskAssign);
			}
		}
		task.setTaskAssigns(taskAssigns);

		// Delete old task assign
		taskAssignsRepository.deleteInBatch(oldTaskAssigns);

		// Save data into DB
		tasksRepository.save(task);

		// Update notification
		notificationFacade.updateNotification(ActivityType.Work.name(), task.getId(), task.getName(),
				!taskAssigns.isEmpty() ? taskAssigns.get(0).getEmployee() : null,
				!taskAssigns.isEmpty() ? taskAssigns.get(0).getEmployeeGroup() : null, task.getBranch(),
				task.getStartDate(), task.getEndDate(), task.getAddress());

		return taskMapper.buildDto(task);
	}
}
