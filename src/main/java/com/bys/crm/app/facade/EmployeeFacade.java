package com.bys.crm.app.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.EmployeeAvatarDto;
import com.bys.crm.app.dto.EmployeeDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.EmployeeMapper;
import com.bys.crm.app.mapping.EmployeeSummaryMapper;
import com.bys.crm.config.security.service.AuthenticatedService;
import com.bys.crm.domain.PageableResult;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class EmployeeFacade {
	@Autowired
	private EmployeeMapper mapper;

	@Autowired
	private EmployeeSummaryMapper employeeSummaryMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	AuthenticatedService authenticatedService;

	// Get employee list
	public List<EmployeeSummaryDto> getEmployeesList(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<HREmployees> employeeList = employeesRepository.findByStatusAndNameNotEmptyOrderByNameAsc(AAStatus.Alive.name());

		return employeeList.stream().map(entity -> employeeSummaryMapper.buildDto(entity)).collect(Collectors.toList());
	}

	// Get employee by id
	public EmployeeDto getEmployeeById(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return mapper.buildDto(employee);
	}

	// Create employee
	@Transactional
	public EmployeeDto createEmployee(EmployeeDto dto, Long userId) {
		if (userId != authenticatedService.getCurrentUser().getId().longValue()) {
			throw new AccessDeniedException("Non authorize user");
		}

		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		HREmployees entity = mapper.buildEntity(dto);
		entity.setCreatedDate(DateTime.now());
		entity.setUpdatedDate(DateTime.now());

		employeesRepository.save(entity);

		return mapper.buildDto(entity);
	}

	// Get employee by branch
	public List<EmployeeDto> getEmployeesListByBranch(Integer employeeId, Long branchId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<HREmployees> employeesList = employeesRepository
				.findByStatusAndBranchIdOrderByNameAsc(AAStatus.Alive.name(), branchId);

		if (employeesList.isEmpty()) {
			throw new InvalidException("Employees by Branch are not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		return employeesList.stream().map(entity -> mapper.buildDto(entity)).collect(Collectors.toList());
	}

	// Get employee list with paging
	public PageableResult<EmployeeDto> getEmployeesPaging(Integer employeeId, Integer pageNumber, Integer pageSize) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		// Get page request list with page number and page size.
		Pageable employeePageRequest = new PageRequest(pageNumber, pageSize);
		Page<HREmployees> employeeList = employeesRepository.findByStatusOrderByNameAsc(AAStatus.Alive.name(),
				employeePageRequest);

		// Convert the list of entity to the list of dto
		List<EmployeeDto> employeeDtos = null;
		if (employeeList != null && employeeList.getContent() != null && !employeeList.getContent().isEmpty()) {
			employeeDtos = new ArrayList<>();
			for (HREmployees entity : employeeList) {
				employeeDtos.add(mapper.buildDto(entity));
			}
		}

		// Return PageableResult
		return new PageableResult<>(pageNumber, employeeList.getTotalPages(), employeeList.getTotalElements(),
				employeeDtos);
	}

	// Edit Employee
	@Transactional
	public EmployeeDto editEmployee(Integer employeeId, EmployeeDto dto) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		// Build entity
		employee.setAvatar(dto.getAvatar());
		employee.setUpdatedDate(DateTime.now());
		employee.setUpdatedUser(employee.getName());

		// Save data into DB
		employeesRepository.save(employee);

		return mapper.buildDto(employee);
	}

	// Get avatar by id
	public EmployeeAvatarDto getAvatarById(Integer employeeId, Integer id) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		EmployeeAvatarDto dto = new EmployeeAvatarDto();

		// Get avatar by id and set it into dto
		dto.setAvatar(employeesRepository.findById(id).getAvatar());

		return dto;
	}
}
