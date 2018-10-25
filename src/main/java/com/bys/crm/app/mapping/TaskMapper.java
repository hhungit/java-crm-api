package com.bys.crm.app.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ActivityDto;
import com.bys.crm.app.dto.EmployeeSummaryDto;
import com.bys.crm.app.dto.GroupSummaryDto;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.HRGroups;
import com.bys.crm.domain.erp.model.PMTaskAssigns;
import com.bys.crm.domain.erp.model.PMTasks;
import com.bys.crm.domain.erp.repository.PMTaskAssignsRepository;
import com.bys.crm.domain.erp.repository.PMTasksRepository;
import com.bys.crm.util.ListUtil;

@Component
public class TaskMapper extends BaseMapper<ActivityDto, PMTasks> {
	@Autowired
	private PMTasksRepository tasksRepository;

	@Autowired
	private PMTaskAssignsRepository taskAssignsRepository;

	@Override
	public ActivityDto buildDto(PMTasks entity) {
		ActivityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		List<EmployeeSummaryDto> employeeDtos = new ArrayList<>();
		List<GroupSummaryDto> groupDtos = new ArrayList<>();
		EmployeeSummaryDto employeeDto = null;
		GroupSummaryDto groupDto = null;

		for (PMTaskAssigns taskAssign : entity.getTaskAssigns()) {
			if (taskAssign.getEmployee() != null) {
				employeeDto = new EmployeeSummaryDto();
				employeeDto.setId(taskAssign.getEmployee().getId());
				employeeDto.setName(taskAssign.getEmployee().getName());
				employeeDtos.add(employeeDto);
			}

			if (taskAssign.getEmployeeGroup() != null) {
				groupDto = new GroupSummaryDto();
				groupDto.setId(taskAssign.getEmployeeGroup().getId());
				groupDto.setName(taskAssign.getEmployeeGroup().getName());
				groupDtos.add(groupDto);
			}
		}

		dto.setEmployees(employeeDtos);
		dto.setEmployeeGroups(groupDtos);

		return dto;
	}

	@Override
	public PMTasks buildEntity(ActivityDto dto) {
		PMTasks task = super.buildEntity(dto);
		if (dto.getId() == null) {
			task.setId(keyGenerationService.findMaxId(tasksRepository).incrementAndGet());
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			task.setBranch(null);
		}

		PMTaskAssigns taskAssign = null;
		HREmployees employee = null;
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

		return task;
	}

	public ActivityDto buildDto(PMTasks entity, Integer employeeId, ArrayList<Integer> groupIds) {
		ActivityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		List<EmployeeSummaryDto> employeeDtos = new ArrayList<>();
		List<GroupSummaryDto> groupDtos = new ArrayList<>();
		EmployeeSummaryDto employeeDto = null;
		GroupSummaryDto groupDto = null;

		for (PMTaskAssigns taskAssign : entity.getTaskAssigns()) {
			if (taskAssign.getEmployee() != null) {
				employeeDto = new EmployeeSummaryDto();
				employeeDto.setId(taskAssign.getEmployee().getId());
				employeeDto.setName(taskAssign.getEmployee().getName());
				employeeDtos.add(employeeDto);
			}

			if (taskAssign.getEmployeeGroup() != null) {
				groupDto = new GroupSummaryDto();
				groupDto.setId(taskAssign.getEmployeeGroup().getId());
				groupDto.setName(taskAssign.getEmployeeGroup().getName());
				groupDtos.add(groupDto);
			}
		}

		dto.setEmployees(employeeDtos);
		dto.setEmployeeGroups(groupDtos);

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getTaskAssigns().get(0).getEmployeeGroup() != null
						? entity.getTaskAssigns().get(0).getEmployeeGroup().getId()
						: null,
				entity.getTaskAssigns().get(0).getEmployee() != null
						? entity.getTaskAssigns().get(0).getEmployee().getId()
						: null));

		return dto;
	}

}