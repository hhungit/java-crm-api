package com.bys.crm.app.mapping;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CountryDto;
import com.bys.crm.domain.erp.model.GECountrys;

@Component
public class CountryMapper extends BaseMapper<CountryDto, GECountrys> {

	@Override
	public CountryDto buildDto(GECountrys entity) {
		CountryDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public GECountrys buildEntity(CountryDto dto) {
		GECountrys entity = super.buildEntity(dto);

		return entity;
	}

}
