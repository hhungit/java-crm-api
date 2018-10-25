package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.GroupDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.repository.HRGroupsRepository;
import com.bys.crm.domain.erp.service.KeyGenerationService;

@Service
public class GroupFacade {
	@Autowired
	private GenericMapper mapper;

	@Autowired
	private HRGroupsRepository groupsRepository;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private KeyGenerationService keyGenerationService;

	// Create employee
	@Transactional
	public GroupDto createGroup(GroupDto dto, Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		HRGroups entity = mapper.buildObject(dto, HRGroups.class);
		entity.setId(Long.valueOf(keyGenerationService.findMaxId(groupsRepository).incrementAndGet()).intValue());

		groupsRepository.save(entity);

		return mapper.buildObject(entity, GroupDto.class);
	}

	// Add Employee To Groups
//	@Transactional
//	public String addEmployeeToGroup(Integer employeeId, Integer empId, Long groupId) {
//		// Validate employee id
//		if (employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId) == null) {
//			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
//		}
//
//		HRGroups groupEntity = groupsRepository.findByIdAndStatus(groupId, AAStatus.Alive.name());
//		if (groupEntity == null) {
//			throw new InvalidException("Group is not exist.", ErrorCodeEnum.INVALID_REQUEST);
//		}
//
//		Set<HRGroups> groups = new HashSet<>();
//		groups.add(groupEntity);
//
//		HREmployees employeeEntity = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), empId);
//		if (employeeEntity == null) {
//			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.INVALID_REQUEST);
//		}
//		employeeEntity.setGroups(groups);
//
//		employeesRepository.save(employeeEntity);
//
//		// return success message
//		return StatusMessengerEnum.Successful.name();
//	}

	// Get group by id
	public GroupDto getGroupById(Integer employeeId, Integer groupId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		HRGroups entity = groupsRepository.findByIdAndStatus(groupId, AAStatus.Alive.name());

		return mapper.buildObject(entity, GroupDto.class);
	}

	// Get group list
	public List<GroupDto> getGroupList(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<HRGroups> entities = groupsRepository.findByStatus(AAStatus.Alive.name());

		return mapper.buildObjects(entities, GroupDto.class);
	}
}
