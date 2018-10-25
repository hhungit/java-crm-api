package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CustomerContactDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.Name;
import com.bys.crm.domain.erp.constant.PotentialSource;
import com.bys.crm.util.StringUtil;

@Component
public class ContactValidator {

	public void validate(CustomerContactDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtil.isNotEmpty(dto.getTitle()) && Name.fromName(dto.getTitle()) == null) {
			throw new InvalidException("Invalid Name values is " + Name.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

//		if (StringUtil.isNotEmpty(dto.getPotentialSource()) && PotentialSource.fromName(dto.getPotentialSource()) == null) {
//			throw new InvalidException("Valid PotentialSource values is " + PotentialSource.supportValues(),
//					ErrorCodeEnum.INVALID_TYPE);
//		}
	}

	public void validateFilter(String potentialSource) {
//		if (StringUtil.isNotEmpty(potentialSource) && PotentialSource.fromName(potentialSource) == null) {
//			throw new InvalidException("Valid PotentialSource values is " + PotentialSource.supportValues(),
//					ErrorCodeEnum.INVALID_TYPE);
//		}
	}
}
