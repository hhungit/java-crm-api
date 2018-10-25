package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ChangeProspectDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.Business;
import com.bys.crm.domain.erp.constant.Name;
import com.bys.crm.util.StringUtil;

@Component
public class ChangeProspectValidator {

	public void validate(ChangeProspectDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}
		if (StringUtil.isNotEmpty(dto.getBusiness()) && Business.fromName(dto.getBusiness()) == null) {
			throw new InvalidException("Invalid Business values is " + Business.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
		if (StringUtil.isNotEmpty(dto.getTitle()) && Name.fromName(dto.getTitle()) == null) {
			throw new InvalidException("Invalid Name values is " + Name.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}
}
