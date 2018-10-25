package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CustomerDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.CustomerType;
import com.bys.crm.domain.erp.constant.Business;
import com.bys.crm.domain.erp.constant.CustomerClassify;
import com.bys.crm.domain.erp.constant.CustomerGroup;
import com.bys.crm.util.StringUtil;

@Component
public class CustomerValidator {

	public void validate(CustomerDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}
		
		if (StringUtil.isNotEmpty(dto.getClassify()) && CustomerClassify.fromName(dto.getClassify()) == null) {
			throw new InvalidException("Valid CustomerClassify values is " + CustomerClassify.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getGroup()) && CustomerGroup.fromName(dto.getGroup()) == null) {
			throw new InvalidException("Valid CustomerGroup values is " + CustomerGroup.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getBusiness()) && Business.fromName(dto.getBusiness()) == null) {
			throw new InvalidException("Valid Business values is " + Business.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		// Validate customer type
		if (StringUtil.isNotEmpty(dto.getCustomerType()) && CustomerType.fromName(dto.getCustomerType()) == null) {
			throw new InvalidException("Valid CustomerType values is " + CustomerType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}

	public void validateFilter(String business, String group) {
		if (StringUtil.isNotEmpty(business) && Business.fromName(business) == null) {
			throw new InvalidException("Valid Business values is " + Business.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(group) && CustomerGroup.fromName(group) == null) {
			throw new InvalidException("Valid CustomerGroup values is " + CustomerGroup.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}
}
