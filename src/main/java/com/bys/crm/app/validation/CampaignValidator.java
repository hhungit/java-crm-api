package com.bys.crm.app.validation;

import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CampaignDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.constant.CampaignStatus;
import com.bys.crm.domain.erp.constant.CampaignType;
import com.bys.crm.util.StringUtil;

@Component
public class CampaignValidator {

	public void validate(CampaignDto dto) {
		// Validation Input data
		if (dto == null) {
			throw new InvalidException("Input data is required.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtil.isNotEmpty(dto.getType()) && CampaignType.fromName(dto.getType()) == null) {
			throw new InvalidException("Valid CampaignType values is " + CampaignType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(dto.getCampaignStatus()) && CampaignStatus.fromName(dto.getCampaignStatus()) == null) {
			throw new InvalidException("Valid CampaignStatus values is " + CampaignStatus.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}

	public void validateFilter(String type, String campaignStatus) {

		if (StringUtil.isNotEmpty(type) && CampaignType.fromName(type) == null) {
			throw new InvalidException("Valid CampaignType values is " + CampaignType.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}

		if (StringUtil.isNotEmpty(campaignStatus) && CampaignStatus.fromName(campaignStatus) == null) {
			throw new InvalidException("Valid CampaignStatus values is " + CampaignStatus.supportValues(),
					ErrorCodeEnum.INVALID_TYPE);
		}
	}
}
