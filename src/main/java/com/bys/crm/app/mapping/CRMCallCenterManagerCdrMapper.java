package com.bys.crm.app.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CRMCallCenterManagerDto;
import com.bys.crm.domain.erp.model.CRMCallCenterManagerCdrs;
import com.bys.crm.domain.erp.repository.CRMCallCenterManagerCdrsRepository;

@Component
public class CRMCallCenterManagerCdrMapper extends BaseMapper<CRMCallCenterManagerDto, CRMCallCenterManagerCdrs>{

	@Autowired
	private CRMCallCenterManagerCdrsRepository repository;
	
	@Override
	public CRMCallCenterManagerCdrs buildEntity(CRMCallCenterManagerDto dto) {
		CRMCallCenterManagerCdrs entity = super.buildEntity(dto);
		entity.setId(keyGenerationService.findMaxId(repository).incrementAndGet());
		return entity;
	}
}
