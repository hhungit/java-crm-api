package com.bys.crm.app.mapping;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CampaignDto;
import com.bys.crm.domain.erp.constant.ADObjectType;
import com.bys.crm.domain.erp.model.ARCampaigns;
import com.bys.crm.domain.erp.repository.ARCampaignsRepository;
import com.bys.crm.util.ListUtil;

@Component
public class CampaignMapper extends BaseMapper<CampaignDto, ARCampaigns> {
	@Autowired
	private ARCampaignsRepository campaignsRepository;

	@Override
	public CampaignDto buildDto(ARCampaigns entity) {
		CampaignDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public ARCampaigns buildEntity(CampaignDto dto) {
		ARCampaigns entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(campaignsRepository).incrementAndGet());
			entity.setCampaignNo(keyGenerationService.generateNumberNo(ADObjectType.CRMCampaign.name()));
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

	public CampaignDto buildDto(ARCampaigns entity, Integer employeeId, ArrayList<Integer> groupIds) {
		CampaignDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null));

		return dto;
	}

}
