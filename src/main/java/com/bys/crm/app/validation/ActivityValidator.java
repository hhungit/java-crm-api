package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ActivityDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.ActivityStatus;
import com.bys.crm.domain.erp.constant.ActivityType;
import com.bys.crm.domain.erp.constant.ActivityObjectType;
import com.bys.crm.domain.erp.constant.EventType;
import com.bys.crm.util.StringUtil;

@Component
public class ActivityValidator {

	public void validate(ActivityDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtil.isNotEmpty(dto.getActivityStatus())
				&& ActivityStatus.fromName(dto.getActivityStatus()) == null) {
			throw new InvalidException("Valid ActivityStatus values is " + ActivityStatus.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getActivityType()) && ActivityType.fromName(dto.getActivityType()) == null) {
			throw new InvalidException("Valid ActivityType values is " + ActivityType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getActivityObjectType())
				&& ActivityObjectType.fromName(dto.getActivityObjectType()) == null) {
			throw new InvalidException("Valid ActivityObjectType values is " + ActivityObjectType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (ActivityType.Event.name().equalsIgnoreCase(dto.getActivityType())
				&& StringUtil.isNotEmpty(dto.getEventType()) && EventType.fromName(dto.getEventType()) == null) {
			throw new InvalidException("Valid EventType values is " + EventType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

	}

	public void validateFilter(String activityType, String activityStatus) {

		if (StringUtil.isNotEmpty(activityStatus)
				&& ActivityStatus.fromName(activityStatus) == null) {
			throw new InvalidException("Valid ActivityStatus values is " + ActivityStatus.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(activityType) && ActivityType.fromName(activityType) == null) {
			throw new InvalidException("Valid ActivityType values is " + ActivityType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

	}
}
