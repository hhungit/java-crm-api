package com.bys.crm.app.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.NotificationDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.event.NotificationEvent;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.NotificationMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ActivityType;
import com.bys.crm.domain.erp.model.ARNotifications;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.model.PMTaskAssigns;
import com.bys.crm.domain.erp.model.PMTasks;
import com.bys.crm.domain.erp.repository.ARNotificationRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class NotificationFacade {

	@Autowired
	private ARNotificationRepository repository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private NotificationMapper mapper;

	@Transactional
	public String changeReadNotification(Integer employeeId, Long notificationId, Boolean read) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		String message = "";
		ARNotifications entity = repository.findByIdAndStatus(notificationId, AAStatus.Alive.name());

		if (read) {
			entity.setRead((short) 1);
			repository.save(entity);
			message = StatusMessengerEnum.NotificationReadSuccessful.name();
		}
		return message;

	}

	@Transactional
	public Long countWaitNotification(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group id list
		ArrayList<Integer> groupIdList = new ArrayList<>();
		if (!employee.getGroups().isEmpty()) {
			employee.getGroups().forEach(group -> {
				groupIdList.add(group.getId());
			});
		}

		Integer[] groupIdArray = groupIdList.isEmpty() ? null : groupIdList.toArray(new Integer[0]);
		return repository.countByEmployeeIdOrGroupIdAndStatusAndRead(employeeId, groupIdArray, AAStatus.Alive.name(),
				(short) 0);
	}

	public ARNotifications buildEntity(Long notificationId, HREmployees employee, BRBranchs branch, String type,
			Long referenceId, String referenceName, DateTime startDate, DateTime endDate, String localion, HRGroups group) {
		ARNotifications entity = new ARNotifications();
		entity.setId(notificationId);
		entity.setEmployee(employee);
		entity.setBranch(branch);
		entity.setObjectType(type);
		entity.setObjectId(referenceId);
		entity.setObjectName(referenceName);
		entity.setStartDate(startDate);
		entity.setEndDate(endDate);
		entity.setLocation(localion);
		entity.setEmployeeGroup(group);
		return entity;
	}

	// Build notification
	@Transactional
	public void buildNotification(HREmployees employee, BRBranchs branch, String type, Long referenceId,
			String referenceName, DateTime startDate, DateTime endDate, String localion, HRGroups group) {
		// build notification
		AtomicLong notificationIdGeneric = this.keyGenerationService.findMaxId(repository);
		List<ARNotifications> notifications = new ArrayList<>();
		notifications.add(buildEntity(notificationIdGeneric.incrementAndGet(), employee, branch, type, referenceId,
				referenceName, startDate, endDate, localion, group));

		// publish event save notification information
		NotificationEvent eventNotification = new NotificationEvent(this, notifications);
		publisher.publishEvent(eventNotification);
	}

	// Get notification list
	public PageableResult<NotificationDto> getNotificationsByEmployee(Integer employeeId, Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group list
		Set<HRGroups> groups = employee.getGroups();

		// Get group id list
		ArrayList<Integer> groupIdList = new ArrayList<>();
		if (!groups.isEmpty()) {
			groups.forEach(group -> {
				groupIdList.add(group.getId());
			});
		}

		Integer[] groupIdArray = groupIdList.isEmpty() ? null : groupIdList.toArray(new Integer[0]);

		Pageable pageRequest = new PageRequest(pageNumber, pageSize, new Sort(Direction.DESC, "id"));
		Page<ARNotifications> notifications = repository.findByEmployeeIdOrGroupIdAndStatusOrderByIdDesc(employeeId,
				groupIdArray, AAStatus.Alive.name(), pageRequest);

		// Convert the list of entity to the list of dto
		List<NotificationDto> dtos = null;
		if (notifications != null && notifications.getContent() != null && !notifications.getContent().isEmpty()) {
			dtos = notifications.getContent().stream()
					.map(notification -> mapper.buildDto(notification)).collect(Collectors.toList());
		}

		return new PageableResult<>(pageNumber, notifications.getTotalPages(), notifications.getTotalElements(), dtos);
	}

	// Get notification list
	public PageableResult<NotificationDto> getNotificationsByStartDate(Integer employeeId, Long startDate, Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get group list
		Set<HRGroups> groups = employee.getGroups();

		// Get group id list
		ArrayList<Integer> groupIdList = new ArrayList<>();
		if (!groups.isEmpty()) {
			groups.forEach(group -> {
				groupIdList.add(group.getId());
			});
		}
		Integer[] groupIdArray = groupIdList.isEmpty() ? null : groupIdList.toArray(new Integer[0]);

		Pageable pageRequest = new PageRequest(pageNumber, pageSize, new Sort(Direction.ASC, "startDate"));
		Page<ARNotifications> notifications = repository.findByStartDateAndStatus(new DateTime(startDate),
				(new DateTime(startDate)).plusDays(1), employeeId, groupIdArray, AAStatus.Alive.name(), pageRequest);

		// Convert the list of entity to the list of dto
		List<NotificationDto> dtos = null;
		if (notifications != null && notifications.getContent() != null && !notifications.getContent().isEmpty()) {
			dtos = notifications.getContent().stream()
					.map(notification -> mapper.buildDto(notification)).collect(Collectors.toList());
		}

		return new PageableResult<>(pageNumber, notifications.getTotalPages(), notifications.getTotalElements(), dtos);
	}

	// Build notification
	@Transactional
	public void buildNotifications(Set<PMTaskAssigns> taskAssigns, PMTasks task) {
		// build notification
		List<ARNotifications> notifications = new ArrayList<>();
		Long newId = null;
		AtomicLong maxId = keyGenerationService.findMaxId(repository);
		
		for (PMTaskAssigns taskAssign: taskAssigns) {
			newId = maxId.incrementAndGet();
			maxId = new AtomicLong(newId);
			notifications.add(buildEntity(newId, taskAssign.getEmployee(), task.getBranch(),
					ActivityType.Work.name(), task.getId(), task.getName(), task.getStartDate(), task.getEndDate(), null, taskAssign.getEmployeeGroup()));
		}

		// publish event save notification information
		NotificationEvent eventNotification = new NotificationEvent(this, notifications);
		publisher.publishEvent(eventNotification);
	}

	// Update notification
	@Transactional
	public void updateNotification(String objectType, Long objectId, String objectName, HREmployees employee, HRGroups group, BRBranchs branch,
			DateTime startDate, DateTime endDate, String location) {
		// Get notification
		List<ARNotifications> notifications = repository.findByObjectTypeAndObjectIdAndStatus(objectType, objectId, AAStatus.Alive.name());

		if (!notifications.isEmpty()) {
			ARNotifications notification = notifications.get(0);
			notification.setEmployee(employee);
			notification.setEmployeeGroup(group);
			notification.setBranch(branch);
			notification.setObjectType(objectType);;
			notification.setObjectId(objectId);;
			notification.setObjectName(objectName);;
			notification.setStartDate(startDate);
			notification.setEndDate(endDate);
			notification.setLocation(location);

			repository.save(notification);
		}
	}

	// Update notification
	@Transactional
	public void deleteNotification(String objectType, Long objectId) {
		// Get notification
		List<ARNotifications> notifications = repository.findByObjectTypeAndObjectIdAndStatus(objectType, objectId, AAStatus.Alive.name());

		if (!notifications.isEmpty()) {
			ARNotifications notification = notifications.get(0);
			notification.setStatus(AAStatus.Delete.name());

			repository.save(notification);
		}
	}
}
