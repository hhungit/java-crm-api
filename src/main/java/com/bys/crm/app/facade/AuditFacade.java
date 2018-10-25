package com.bys.crm.app.facade;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditDisjunction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.AuditDto;
import com.bys.crm.app.dto.AuditValueDto;
import com.bys.crm.app.dto.CustomerContactSummaryDto;
import com.bys.crm.app.dto.CustomerSummaryDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.GroupSummaryDto;
import com.bys.crm.app.dto.ObjectAuditDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.model.ARActivitys;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.AROpportunitys;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.PMTasks;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class AuditFacade {
	@Autowired
	private GenericMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private EntityManager entityManager;

	// Declare constants
	private static final String PROSPECT = "prospect";
	private static final String CUSTOMER = "customer";
	private static final String CONTACT = "contact";
	private static final String OPPORTUNITY = "opportunity";
	private static final String CAMPAIGN = "campaign";
//	private static final String ACTIVITY = "activity";
	private static final String EVENT = "event";
	private static final String WORK = "work";
	private static final String[] FIELDS_IGNORE = { "createdDate", "createdUser", "rev", "revType", "updatedDate",
			"updatedUser" };
	private static final String PROPERTY_NAME_UPDATE_USER = "updatedUser";
	private static final String PROPERTY_NAME_EMPLOYEE_ID = "employeeId";
	private static final String PROPERTY_NAME_EMPLOYEE_GROUP_ID = "employeeGroupId";
	private static final String WHITE_SPACE = " ";

	private DateTimeFormatter dtFormat = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
	private DecimalFormat moneyFormat = new DecimalFormat("#,###");

	// Get audit information by object id
	public PageableResult<AuditDto> getAuditInfoById(Integer employeeId, String objectType, Long objectId,
			Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		AuditReader auditReader = AuditReaderFactory.get(entityManager);

		// Declare and initial local variables
		List<Number> revisions = null;
		List<ARProspectCustomers> prospectEntities = null;
		List<ARCustomers> customerEntities = null;
		List<ARCustomerContacts> contactEntities = null;
		List<AROpportunitys> opportunityEntities = null;
		List<ARActivitys> activityEntities = null;
		List<PMTasks> workEntities = null;
		List<ARCampaigns> campaignEntities = null;
		List<ObjectAuditDto> dtos = null;
		List<AuditDto> auditList = new ArrayList<>();
		AuditDto auditDto = null;

		// Get data from DB and convert into dto
		switch (objectType) {
		case PROSPECT:
			revisions = auditReader.getRevisions(ARProspectCustomers.class, objectId);

			prospectEntities = new ArrayList<>();
			for (Number revision : revisions) {
				prospectEntities.add(auditReader.find(ARProspectCustomers.class, objectId, revision));
			}

			dtos = prospectEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Prospect.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case CUSTOMER:
			revisions = auditReader.getRevisions(ARCustomers.class, objectId);

			customerEntities = new ArrayList<>();
			for (Number revision : revisions) {
				customerEntities.add(auditReader.find(ARCustomers.class, objectId, revision));
			}

			dtos = customerEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Customer.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case CONTACT:
			revisions = auditReader.getRevisions(ARCustomerContacts.class, objectId);

			contactEntities = new ArrayList<>();
			for (Number revision : revisions) {
				contactEntities.add(auditReader.find(ARCustomerContacts.class, objectId, revision));
			}

			dtos = contactEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Contact.name() + " is not exist", ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case OPPORTUNITY:
			revisions = auditReader.getRevisions(AROpportunitys.class, objectId);

			opportunityEntities = new ArrayList<>();
			for (Number revision : revisions) {
				opportunityEntities.add(auditReader.find(AROpportunitys.class, objectId, revision));
			}

			dtos = opportunityEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Opportunity.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case EVENT:
			revisions = auditReader.getRevisions(ARActivitys.class, objectId);

			activityEntities = new ArrayList<>();
			for (Number revision : revisions) {
				activityEntities.add(auditReader.find(ARActivitys.class, objectId, revision));
			}

			dtos = activityEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Activity.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case WORK:
			revisions = auditReader.getRevisions(PMTasks.class, objectId);

			workEntities = new ArrayList<>();
			for (Number revision : revisions) {
				workEntities.add(auditReader.find(PMTasks.class, objectId, revision));
			}

			dtos = workEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Activity.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		case CAMPAIGN:
			revisions = auditReader.getRevisions(ARCampaigns.class, objectId);

			campaignEntities = new ArrayList<>();
			for (Number revision : revisions) {
				campaignEntities.add(auditReader.find(ARCampaigns.class, objectId, revision));
			}

			dtos = campaignEntities.stream().map(item -> mapper.buildObject(item, ObjectAuditDto.class))
					.collect(Collectors.toList());
			if (dtos.isEmpty()) {
				throw new InvalidException(ADObjectType.Campaign.name() + " is not exist",
						ErrorCodeEnum.DATA_NOT_EXIST);
			}
			break;
		}

		// Add audit action update/delete to audit list
		for (int i = (dtos.size() - 1); i >= 1; i--) {
			auditDto = getAuditDto(dtos.get(i - 1), dtos.get(i));
			if (auditDto != null) {
				auditList.add(auditDto);
			}
		}

		// Add audit action create to audit list
		if (dtos.get(0).getRevType() == 0L) {
			auditList.add(new AuditDto(dtos.get(0).getUpdatedUser(), dtos.get(0).getRevType(), null,
					dtos.get(0).getUpdatedDate()));
		}

		// Get total page
		int totalPage = auditList.size() / pageSize.intValue();
		if (auditList.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (auditList.size() % pageSize.intValue()) > 0) {
			recordNumber = auditList.size() % pageSize.intValue();
		}

		// Get result list
		List<AuditDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(auditList.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(auditList.size()), resultList);
	}

	// Get audit information by user
	public List<AuditDto> getAuditInfoByUser(Integer employeeId, Long recordNumber) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		List<AuditDto> auditDtoList = getAuditDtoList(auditReader, employee);
		Collections.sort(auditDtoList);

		List<AuditDto> auditInfoList = new ArrayList<>();
		if (recordNumber > auditDtoList.size()) {
			recordNumber = Long.valueOf(auditDtoList.size());
		}
		for (int i = 0; i < recordNumber; i++) {
			auditInfoList.add(auditDtoList.get(i));
		}

		return auditInfoList;
	}

	// Get audit information by user
	public PageableResult<AuditDto> getAuditInfoByUser(Integer employeeId, Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		List<AuditDto> auditDtoList = getAuditDtoList(auditReader, employee);
		Collections.sort(auditDtoList);

		// Get total page
		int totalPage = auditDtoList.size() / pageSize.intValue();
		if (auditDtoList.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (auditDtoList.size() % pageSize.intValue()) > 0) {
			recordNumber = auditDtoList.size() % pageSize.intValue();
		}

		// Get result list
		List<AuditDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(auditDtoList.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(auditDtoList.size()), resultList);
	}

	// Get audit dto
	private AuditDto getAuditDto(ObjectAuditDto before, ObjectAuditDto after) {
		if (after.getStatus().equalsIgnoreCase(AAStatus.Delete.name())) {
			return new AuditDto(after.getUpdatedUser(), 2L, null, after.getUpdatedDate());
		}

		List<AuditValueDto> auditValueDtoList = new ArrayList<>();
		Field[] fieldList = before.getClass().getDeclaredFields();
		List<String> fieldIgnoreList = new ArrayList<>(Arrays.asList(FIELDS_IGNORE));
		boolean assignFlg = false;
		String employeeNmBefore = null;
		String employeeNmAfter = null;
		String groupNmBefore = null;
		String groupNmAfter = null;

		try {
			for (Field field : fieldList) {
				field.setAccessible(true);
				String beforeValue = null;
				String afterValue = null;

				if (!fieldIgnoreList.contains(field.getName())) {
					if (field.getType().getName().contains("DateTime")) {
						beforeValue = field.get(before) == null ? null
								: dtFormat.print(new DateTime(field.get(before)));
						afterValue = field.get(after) == null ? null
								: dtFormat.print(new DateTime(field.get(after)));
					} else if (field.getType().getName().contains("EmployeeSummaryDto")) {
						if (field.get(before) != null && field.get(after) == null) {
							employeeNmBefore = ((EmployeeSummaryDto) field.get(before)).getName();
							assignFlg = true;
						} else if (field.get(before) == null && field.get(after) != null) {
							employeeNmAfter = ((EmployeeSummaryDto) field.get(after)).getName();
							assignFlg = true;
						} else if (field.get(before) != null && field.get(after) != null) {
							beforeValue = ((EmployeeSummaryDto) field.get(before)).getName();
							afterValue = ((EmployeeSummaryDto) field.get(after)).getName();
							assignFlg = false;
						}
					} else if (field.getType().getName().contains("GroupSummaryDto")) {
						if (field.get(before) != null && field.get(after) == null) {
							groupNmBefore = ((GroupSummaryDto) field.get(before)).getName();
							assignFlg = true;
						} else if (field.get(before) == null && field.get(after) != null) {
							groupNmAfter = ((GroupSummaryDto) field.get(after)).getName();
							assignFlg = true;
						} else if (field.get(before) != null && field.get(after) != null) {
							beforeValue = ((GroupSummaryDto) field.get(before)).getName();
							afterValue = ((GroupSummaryDto) field.get(after)).getName();
							assignFlg = false;
						}
					} else if (field.getType().getName().contains("CustomerSummaryDto")) {
						beforeValue = field.get(before) == null ? null
								: ((CustomerSummaryDto) field.get(before)).getName();
						afterValue = field.get(after) == null ? null
								: ((CustomerSummaryDto) field.get(after)).getName();
					} else if (field.getType().getName().contains("CustomerContactSummaryDto")) {
						beforeValue = field.get(before) == null ? null
								: ((CustomerContactSummaryDto) field.get(before)).getLastName() + " "
										+ ((CustomerContactSummaryDto) field.get(before)).getFirstName();
						afterValue = field.get(after) == null ? null
								: ((CustomerContactSummaryDto) field.get(after)).getLastName() + " "
										+ ((CustomerContactSummaryDto) field.get(after)).getFirstName();
					} else if (field.getType().getName().contains("BigDecimal")) {
						beforeValue = field.get(before) == null ? null
								: moneyFormat.format(new BigDecimal(String.valueOf(field.get(before)))).replace(",",
										".");
						afterValue = field.get(after) == null ? null
								: moneyFormat.format(new BigDecimal(String.valueOf(field.get(after)))).replace(",",
										".");
					} else {
						beforeValue = String.valueOf(field.get(before));
						afterValue = String.valueOf(field.get(after));
					}

					// In the case the before value different the after value
					if (beforeValue != null && afterValue != null && isDifferent(beforeValue, afterValue)) {
						auditValueDtoList.add(new AuditValueDto(field.getName(), beforeValue, afterValue));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// Check the change of assign
		if (assignFlg) {
			if (employeeNmAfter != null) {
				auditValueDtoList.add(new AuditValueDto("assign", groupNmBefore, employeeNmAfter));
			} else if (groupNmAfter != null) {
				auditValueDtoList.add(new AuditValueDto("assign", employeeNmBefore, groupNmAfter));
			}
		}

		return auditValueDtoList.isEmpty() ? null
				: new AuditDto(after.getUpdatedUser(), after.getRevType(), auditValueDtoList,
						after.getUpdatedDate());
	}

	// Get list AuditDto
	@SuppressWarnings("unchecked")
	private List<AuditDto> getAuditDtoList(AuditReader auditReader, HREmployees employee) {
		AuditQuery query = null;
		List<AuditDto> auditList = new ArrayList<>();
		// Get group id list
		ArrayList<Integer> groupIdList = new ArrayList<>();
		if (!employee.getGroups().isEmpty()) {
			employee.getGroups().forEach(group -> {
				groupIdList.add(group.getId());
			});
		}
		AuditDisjunction condition = AuditEntity.disjunction();
		if (!groupIdList.isEmpty()) {
			condition.add(AuditEntity.property(PROPERTY_NAME_UPDATE_USER).eq(employee.getName()))
					.add(AuditEntity.property(PROPERTY_NAME_EMPLOYEE_ID).eq(employee.getId()))
					.add(AuditEntity.property(PROPERTY_NAME_EMPLOYEE_GROUP_ID).in(groupIdList));
		} else {
			condition.add(AuditEntity.property(PROPERTY_NAME_UPDATE_USER).eq(employee.getName()))
			.add(AuditEntity.property(PROPERTY_NAME_EMPLOYEE_ID).eq(employee.getId()));
		}

		AuditDisjunction conditionAuditWork = AuditEntity.disjunction();
		conditionAuditWork.add(AuditEntity.property(PROPERTY_NAME_UPDATE_USER).eq(employee.getName()));

		// get list audit information of Prospect
		query = auditReader.createQuery().forRevisionsOfEntity(ARProspectCustomers.class, true, true);
		query.add(condition);

		List<ARProspectCustomers> prospectEntities = query.getResultList();
		prospectEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), PROSPECT, item.getId(),
					item.getLastName() + WHITE_SPACE + item.getFirstName(), item.getUpdatedUserId()));
		});

		// get list audit information of Customer
		query = auditReader.createQuery().forRevisionsOfEntity(ARCustomers.class, true, true);
		query.add(condition);

		List<ARCustomers> customerEntities = query.getResultList();
		customerEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), CUSTOMER, item.getId(), item.getName(), item.getUpdatedUserId()));
		});

		// get list audit information of Contact
		query = auditReader.createQuery().forRevisionsOfEntity(ARCustomerContacts.class, true, true);
		query.add(condition);

		List<ARCustomerContacts> contactEntities = query.getResultList();
		contactEntities.stream().forEach(item -> {
			auditList
					.add(new AuditDto(item.getUpdatedUser(),
							(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2
									: item.getRevType(),
							item.getUpdatedDate(), CONTACT, item.getId(),
							item.getLastName() + WHITE_SPACE + item.getFirstName(), item.getUpdatedUserId()));
		});

		// get list audit information of Opportunity
		query = auditReader.createQuery().forRevisionsOfEntity(AROpportunitys.class, true, true);
		query.add(condition);

		List<AROpportunitys> opportunityEntities = query.getResultList();
		opportunityEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), OPPORTUNITY, item.getId(), item.getName(), item.getUpdatedUserId()));
		});

		// get list audit information of Campaign
		query = auditReader.createQuery().forRevisionsOfEntity(ARCampaigns.class, true, true);
		query.add(condition);

		List<ARCampaigns> campaignEntities = query.getResultList();
		campaignEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), CAMPAIGN, item.getId(), item.getName(), item.getUpdatedUserId()));
		});

		// get list audit information of Activity
		query = auditReader.createQuery().forRevisionsOfEntity(ARActivitys.class, true, true);
		query.add(condition);

		List<ARActivitys> activityEntities = query.getResultList();
		activityEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), item.getActivityType().toLowerCase(), item.getId(), item.getName(), item.getUpdatedUserId()));
		});

		query = auditReader.createQuery().forRevisionsOfEntity(PMTasks.class, true, true);
		query.add(conditionAuditWork);
		List<PMTasks> workEntities = query.getResultList();
		workEntities.stream().forEach(item -> {
			auditList.add(new AuditDto(item.getUpdatedUser(),
					(item.getRevType() == 1 && AAStatus.Delete.name().equalsIgnoreCase(item.getStatus())) ? 2 : item.getRevType(),
					item.getUpdatedDate(), WORK, item.getId(), item.getName(), item.getUpdatedUserId()));
		});

		return auditList;
	}

	// Compare 2 objects
	private boolean isDifferent(Object before, Object after) {
		return before != null ? !before.equals(after) : (after != null ? !after.equals(before) : false);
	}
}
