package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ProspectCustomerDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.Business;
import com.bys.crm.domain.erp.constant.Name;
import com.bys.crm.domain.erp.constant.PotentialRate;
import com.bys.crm.domain.erp.constant.PotentialSource;
import com.bys.crm.domain.erp.constant.PotentialStatus;
import com.bys.crm.util.StringUtil;

@Component
public class ProspectCustomerValidator {

	public void validate(ProspectCustomerDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}
		
		if (StringUtil.isNotEmpty(dto.getTitle()) && Name.fromName(dto.getTitle()) == null) {
			throw new InvalidException("Invalid Name values is " + Name.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getRate()) && PotentialRate.fromName(dto.getRate()) == null) {
			throw new InvalidException("Invalid PotentialRate values is " + PotentialRate.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getPotentialStatus()) && PotentialStatus.fromName(dto.getPotentialStatus()) == null) {
			throw new InvalidException("Invalid PotentialStatus values is " + PotentialStatus.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getObjectType()) && dto.getObjectId() == null) {
			throw new InvalidException("ObjectType and ObjectId must be entered together",
					ErrorCodeEnum.INVALID_VALUE);
		}
	}

	public void validateFilter(String potentialRate, String business) {

		if (StringUtil.isNotEmpty(potentialRate) && PotentialRate.fromName(potentialRate) == null) {
			throw new InvalidException("Invalid PotentialRate values is " + PotentialRate.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(business) && Business.fromName(business) == null) {
			throw new InvalidException("Invalid Business values is " + Business.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}
}
