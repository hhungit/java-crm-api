package com.bys.crm.app.mapping;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ActivityDto;
import com.bys.crm.domain.erp.model.ARActivitys;
import com.bys.crm.domain.erp.repository.ARActivitysRepository;
import com.bys.crm.util.ListUtil;

@Component
public class ActivityMapper extends BaseMapper<ActivityDto, ARActivitys> {
	@Autowired
	private ARActivitysRepository activitysRepository;

	@Override
	public ActivityDto buildDto(ARActivitys entity) {
		ActivityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public ARActivitys buildEntity(ActivityDto dto) {
		ARActivitys entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(activitysRepository).incrementAndGet());
		}

		if (dto.getEmployee() != null && dto.getEmployee().getId() == null) {
			entity.setEmployee(null);
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			entity.setBranch(null);
		}

		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() == null) {
			entity.setEmployeeGroup(null);
		}

		return entity;
	}

	public ActivityDto buildDto(ARActivitys entity, Integer employeeId, ArrayList<Integer> groupIds) {
		ActivityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null));

		return dto;
	}
}
