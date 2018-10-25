package com.bys.crm.app.mapping;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.StateProvinceDto;
import com.bys.crm.domain.erp.model.GEStateProvinces;

@Component
public class StateProvinceMapper extends BaseMapper<StateProvinceDto, GEStateProvinces> {

	@Override
	public StateProvinceDto buildDto(GEStateProvinces entity) {
		StateProvinceDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public GEStateProvinces buildEntity(StateProvinceDto dto) {
		GEStateProvinces entity = super.buildEntity(dto);

		if (dto.getCountry() != null && dto.getCountry().getId() == null) {
			entity.setCountry(null);
		}

		return entity;
	}

}
