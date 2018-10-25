package com.bys.crm.app.mapping;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.DistrictDto;
import com.bys.crm.domain.erp.model.GEDistricts;

@Component
public class DistrictMapper extends BaseMapper<DistrictDto, GEDistricts> {

	@Override
	public DistrictDto buildDto(GEDistricts entity) {
		DistrictDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public GEDistricts buildEntity(DistrictDto dto) {
		GEDistricts entity = super.buildEntity(dto);

		if (dto.getProvince() != null && dto.getProvince().getId() == null) {
			entity.setProvince(null);
		}

		return entity;
	}

}
