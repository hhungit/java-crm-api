package com.bys.crm.app.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CRMCallCenterManagerDto;
import com.bys.crm.domain.erp.model.CRMCallCenterManagers;
import com.bys.crm.domain.erp.repository.CRMCallCenterManagersRepository;

@Component
public class CRMCallCenterManagerMapper extends BaseMapper<CRMCallCenterManagerDto, CRMCallCenterManagers>{

	@Autowired
	private CRMCallCenterManagersRepository repository;
	
	@Override
	public CRMCallCenterManagers buildEntity(CRMCallCenterManagerDto dto) {
		CRMCallCenterManagers entity = super.buildEntity(dto);
		entity.setId(keyGenerationService.findMaxId(repository).incrementAndGet());
		return entity;
	}
	
	@Override
	public CRMCallCenterManagerDto buildDto(CRMCallCenterManagers entity) {
		CRMCallCenterManagerDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}
}
