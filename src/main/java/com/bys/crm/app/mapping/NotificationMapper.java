package com.bys.crm.app.mapping;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.NotificationDto;
import com.bys.crm.domain.erp.model.ARNotifications;

@Component
public class NotificationMapper extends BaseMapper<NotificationDto, ARNotifications> {

	@Override
	public NotificationDto buildDto(ARNotifications entity) {
		NotificationDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public ARNotifications buildEntity(NotificationDto dto) {
		ARNotifications entity = super.buildEntity(dto);

		return entity;
	}
}
