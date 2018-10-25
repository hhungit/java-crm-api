package com.bys.crm.app.mapping;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.BranchSummaryDto;
import com.bys.crm.app.dto.LocationDto;
import com.bys.crm.app.dto.OpportunityDto;
import com.bys.crm.domain.erp.model.AROpportunitys;
import com.bys.crm.domain.erp.repository.AROpportunitysRepository;
import com.bys.crm.util.ListUtil;

@Component
public class OpportunityMapper extends BaseMapper<OpportunityDto, AROpportunitys> {
	@Autowired
	private AROpportunitysRepository opportunitysRepository;

	@Autowired
	private GenericMapper mapper;
	
	@Override
	public OpportunityDto buildDto(AROpportunitys entity) {
		OpportunityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}
		if (entity.getBranch() != null) {
			dto.setBranch(this.mapper.buildObject(entity.getBranch(), BranchSummaryDto.class));
//			if (entity.getBranch().getName() != null) {
//				dto.getBranch()
//						.setName(this.mapper.buildObject(entity.getBranch().getName(), BranchSummaryDto.class));
//			}
		}
		return dto;
	}

	@Override
	public AROpportunitys buildEntity(OpportunityDto dto) {
		AROpportunitys entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(opportunitysRepository).incrementAndGet());
		}

		if (dto.getCustomer() != null && dto.getCustomer().getId() == null) {
			entity.setCustomer(null);
		}

//		if (dto.getCustomerContact() != null && dto.getCustomerContact().getId() == null) {
//			entity.setCustomerContact(null);
//		}

		if (dto.getEmployee() != null && dto.getEmployee().getId() == null) {
			entity.setEmployee(null);
		}

		if (dto.getBranch() != null && dto.getBranch().getId() == null) {
			entity.setBranch(null);
		}

		if (dto.getCampaign() != null && dto.getCampaign().getId() == null) {
			entity.setCampaign(null);
		}

		if (dto.getEmployeeGroup() != null && dto.getEmployeeGroup().getId() == null) {
			entity.setEmployeeGroup(null);
		}

		if (dto.getCustomerResource() != null && dto.getCustomerResource().getId() == null) {
			entity.setCustomerResource(null);
		}

		return entity;
	}

	public OpportunityDto buildDto(AROpportunitys entity, Integer employeeId, ArrayList<Integer> groupIds) {
		OpportunityDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		dto.setIsAssigned(ListUtil.isAssignedToEmployee(employeeId, groupIds,
				entity.getEmployeeGroup() != null ? entity.getEmployeeGroup().getId() : null,
				entity.getEmployee() != null ? entity.getEmployee().getId() : null));

		return dto;
	}

}
