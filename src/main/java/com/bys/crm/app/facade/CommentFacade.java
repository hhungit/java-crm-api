package com.bys.crm.app.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.CommentDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.CommentMapper;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.model.ARActivitys;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.model.ARComments;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;
import com.bys.crm.domain.erp.model.AROpportunitys;
import com.bys.crm.domain.erp.model.ARProspectCustomers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.PMTasks;
import com.bys.crm.domain.erp.repository.ARActivitysRepository;
import com.bys.crm.domain.erp.repository.ARCampaignsRepository;
import com.bys.crm.domain.erp.repository.ARCommentsRepository;
import com.bys.crm.domain.erp.repository.ARCustomerContactRepository;
import com.bys.crm.domain.erp.repository.ARCustomersRepository;
import com.bys.crm.domain.erp.repository.AROpportunitysRepository;
import com.bys.crm.domain.erp.repository.ARProspectCustomersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.repository.PMTasksRepository;

@Service
public class CommentFacade {
	@Autowired
	private CommentMapper mapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ARProspectCustomersRepository prospectCustomersRepository;

	@Autowired
	private ARCustomersRepository customersRepository;

	@Autowired
	private ARCustomerContactRepository customerContactRepository;

	@Autowired
	private AROpportunitysRepository opportunitysRepository;

	@Autowired
	private ARCampaignsRepository campaignsRepository;

	@Autowired
	private ARActivitysRepository activitysRepository;

	@Autowired
	private ARCommentsRepository commentsRepository;

	@Autowired
	private PMTasksRepository tasksRepository;

	// Declare constants
	private static final Long ZERO = 0L;
	private static final String PROSPECT = "prospect";
	private static final String CUSTOMER = "customer";
	private static final String CONTACT = "contact";
	private static final String OPPORTUNITY = "opportunity";
	private static final String CAMPAIGN = "campaign";
//	private static final String ACTIVITY = "activity";
	private static final String EVENT = "event";
	private static final String WORK = "work";

	// Create comment
	@Transactional
	public CommentDto createComment(CommentDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validate type
		if (ADObjectType.fromValue(dto.getType()) == null) {
			throw new InvalidException("Valid comment type values is" + ADObjectType.supportValues(),
					ErrorCodeEnum.INVALID_COMMENT_TYPE);
		}

		ARProspectCustomers prospect = null;
		ARCustomers customer = null;
		ARCustomerContacts contact = null;
		AROpportunitys opportunity = null;
		ARCampaigns campaign = null;
//		ARActivitys activity = null;
		ARActivitys event = null;
		PMTasks work = null;

		switch (dto.getType()) {
		case PROSPECT:
			prospect = prospectCustomersRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case CUSTOMER:
			customer = customersRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case CONTACT:
			contact = customerContactRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case OPPORTUNITY:
			opportunity = opportunitysRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case CAMPAIGN:
			campaign = campaignsRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case EVENT:
			event = activitysRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		case WORK:
			work = tasksRepository.findByIdAndStatus(dto.getObjectId(), AAStatus.Alive.name());
			break;
		}

		if (prospect == null && customer == null && contact == null && opportunity == null && campaign == null
				&& event == null && work == null) {
			throw new InvalidException(ADObjectType.fromValue(dto.getType()).name()+" type with id "+dto.getId() + " is not exist.",
					ErrorCodeEnum.INVALID_REQUEST);
		}

		// Convert data from dto to entity
		ARComments comment = mapper.buildEntity(dto);
		comment.setType(dto.getType().toLowerCase());
		comment.setObjectId(dto.getObjectId());
		comment.setParentId(dto.getParentId() == null ? 0 : dto.getParentId());
		comment.setDate(DateTime.now());
		comment.setCreatedDate(DateTime.now());
		comment.setUpdatedDate(DateTime.now());
		comment.setCreatedUser(employee.getName());
		comment.setUpdatedUser(employee.getName());
		comment.setEmployee(employee);

		// Save data into DB
		commentsRepository.save(comment);

		return mapper.buildDto(comment);
	}

	// Get comment list
	public PageableResult<CommentDto> getCommentList(Integer employeeId, String type, Long objectId,
			Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validate type
		if (ADObjectType.fromValue(type) == null) {
			throw new InvalidException("Valid object type values is " + ADObjectType.supportValues(),
					ErrorCodeEnum.INVALID_COMMENT_TYPE);
		}

		// Get list comment that have not parent comment
		List<ARComments> commentsList = commentsRepository.findByStatusAndTypeAndObjectIdAndParentIdOrderByIdDesc(
				AAStatus.Alive.name(), ADObjectType.fromValue(type).name(), objectId, ZERO);
		if (commentsList.isEmpty()) {
			throw new InvalidException("Comment is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<CommentDto> commentDtos = commentsList.stream().map(item -> {
			List<CommentDto> dtos = new ArrayList<>();
			CommentDto dto = mapper.buildDto(item);
			List<ARComments> childrens = commentsRepository.findByStatusAndTypeAndObjectIdAndParentIdOrderByIdDesc(
					AAStatus.Alive.name(), ADObjectType.fromValue(type).name(), objectId, item.getId());
			if (CollectionUtils.isNotEmpty(childrens)) {
				childrens.stream().forEach(comment -> {
					CommentDto commentDto = mapper.buildDto(comment);
					dtos.add(commentDto);
				});
			}
			dto.setChildren(dtos);

			return dto;
		}).collect(Collectors.toList());

		// Get total page
		int totalPage = commentDtos.size() / pageSize.intValue();
		if (commentDtos.size() % pageSize.intValue() > 0) {
			totalPage = totalPage + 1;
		}

		// Get record number for displaying
		int recordNumber = pageSize;
		if (totalPage == (pageNumber + 1) && (commentDtos.size() % pageSize.intValue()) > 0) {
			recordNumber = commentDtos.size() % pageSize.intValue();
		}

		// Get result list
		List<CommentDto> resultList = new ArrayList<>();
		if (pageNumber < totalPage) {
			for (int i = (pageNumber * pageSize); i < ((pageNumber * pageSize) + recordNumber); i++) {
				resultList.add(commentDtos.get(i));
			}
		}

		return new PageableResult<>(pageNumber, totalPage, Long.valueOf(commentDtos.size()), resultList);
	}

}
