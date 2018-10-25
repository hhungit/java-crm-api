package com.bys.crm.app.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.service.CommonService;

@Component
public class CommonValidator {

	@Autowired
	public CommonService commonService;

	// Check exist phone number
	public void checkExistNumberPhone(String phone) {
		if (commonService.isExistPhoneNumber(phone)) {
			throw new InvalidException(phone, ErrorCodeEnum.EXIST_PHONE_NUMBER);
		};
	}
}
