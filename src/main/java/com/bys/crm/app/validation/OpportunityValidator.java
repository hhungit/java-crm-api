package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.OpportunityDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.OpportunityClassify;
import com.bys.crm.domain.erp.constant.OpportunityStep;
import com.bys.crm.domain.erp.constant.PotentialSource;
import com.bys.crm.util.StringUtil;

@Component
public class OpportunityValidator {

	public void validate(OpportunityDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

//		if (StringUtil.isNotEmpty(dto.getPotentialSources()) && PotentialSource.fromName(dto.getPotentialSources()) == null) {
//			throw new InvalidException("Valid PotentialSource values is " + PotentialSource.supportValues(),
//					ErrorCodeEnum.INVALID_TYPE);
//		}

		if (StringUtil.isNotEmpty(dto.getClassify()) && OpportunityClassify.fromName(dto.getClassify()) == null) {
			throw new InvalidException("Valid OpportunityClassify values is " + OpportunityClassify.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getStep()) && OpportunityStep.fromName(dto.getStep()) == null) {
			throw new InvalidException("Valid OpportunityStep values is " + OpportunityStep.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}

	public void validateFilter(String classify, String step) {
		if (StringUtil.isNotEmpty(classify) && OpportunityClassify.fromName(classify) == null) {
			throw new InvalidException("Valid OpportunityClassify values is " + OpportunityClassify.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(step) && OpportunityStep.fromName(step) == null) {
			throw new InvalidException("Valid OpportunityStep values is " + OpportunityStep.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}

}
