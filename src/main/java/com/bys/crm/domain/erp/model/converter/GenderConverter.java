package com.bys.crm.domain.erp.model.converter;

import javax.persistence.AttributeConverter;

import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.util.StringUtil;


public class GenderConverter implements AttributeConverter<Gender, String>{

	@Override
	public String convertToDatabaseColumn(Gender gender) {
		return gender == null? null: gender.name();
		
	}

	@Override
	public Gender convertToEntityAttribute(String dbData) {
		return StringUtil.isEmpty(dbData)? null: Gender.valueOf(dbData);
	}

}

