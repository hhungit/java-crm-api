package com.bys.crm.app.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.AddressDto;
import com.bys.crm.app.dto.CompanySignupDto;
import com.bys.crm.app.dto.CustomerDto;
import com.bys.crm.app.dto.UserSignupDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.GELocations;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.domain.erp.repository.GELocationsRepository;

@Component
public class SignupValidator {

	private static String PHONE_NUMBER_EXPRESSION = "[0-9]+";
	private static String TAX_NUMBER_EXPRESSION = "[0-9]{10}(-[0-9]{3})?";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	@Autowired
	private ADUsersRepository adPortalUsersRepository;

	@Autowired
	private GELocationsRepository geLocationsRepository;

	@Autowired
	private PasswordValidator passwordValidator;

	public void validate(UserSignupDto dto) {
		DateTime dateTime = new DateTime(dto.getDob());
		if (dateTime.isAfter(DateTime.now()) || dateTime.isBefore(DateTime.now().minusYears(100))) {
			throw new InvalidException("DOB must be within 100 years " + DATE_FORMAT, ErrorCodeEnum.IVALID_DATE);
		}
		validateUserName(dto.getEmail());
		passwordValidator.validatePassword(dto.getPassword());
		validatePhone(dto.getPhone());
		if(dto.getAddress() != null){
			dto.setLocation(validateAddress(dto.getAddress()));
		}
	}

	private void validateUserName(String username) {
		if (!adPortalUsersRepository.findByEmployeeEmailAndStatus(username, AAStatus.Alive.name()).isEmpty()) {
			throw new InvalidException("Email existed already", ErrorCodeEnum.EMAIL_EXISTED_ALREADY);
		}
	}

	private GELocations validateAddress(AddressDto dto) {
		Long provinceId = dto.getProvinceId();
		Long districtId = dto.getDistrictId();
		GELocations location = null;
		if (districtId == null) {
			location = geLocationsRepository.findOne(provinceId);
			if (location == null) {
				throw new ResourceNotFoundException("Province id not found");
			}

		} else {
			location = geLocationsRepository.findOne(districtId);
			if (location == null) {
				throw new ResourceNotFoundException("District id not found");
			}
			GELocations parent = location.getParent();
			if (parent == null || !parent.getId().equals(provinceId)) {
				throw new InvalidException("Mismatch province and district", ErrorCodeEnum.INVALID_ADDRESS);
			}
		}
		return location;
	}

	public void validate(CompanySignupDto dto) {
		validateUserName(dto.getEmail());
		passwordValidator.validatePassword(dto.getPassword());
		validatePhone(dto.getPhone());
		if(StringUtils.isNotBlank(dto.getCompanyPhone())){
			validatePhone(dto.getCompanyPhone());
		}
		if (dto.getAddress() != null) {
			validateAddress(dto.getAddress());
		}
		validateTaxNumber(dto.getTaxNumber());
	}

	private void validatePhone(String phoneNumber) {
		Pattern pattern = Pattern.compile(PHONE_NUMBER_EXPRESSION);
		Matcher matcher = pattern.matcher(phoneNumber);
		if (!matcher.matches()) {
			throw new InvalidException("Invalid phone number " + phoneNumber, ErrorCodeEnum.INVALID_PHONE_NUMBER);
		}
	}

	public void validate(CustomerDto dto) {
		DateTime dateTime = new DateTime(dto.getDob());
		if (dateTime.isAfter(DateTime.now()) || dateTime.isBefore(DateTime.now().minusYears(100))) {
			throw new InvalidException("DOB must be within 100 years " + DATE_FORMAT, ErrorCodeEnum.IVALID_DATE);
		}
		validateUserName(dto.getEmail());
		validatePhone(dto.getTel1());
	}

	private void validateTaxNumber(String taxNumber) {
		Pattern pattern = Pattern.compile(TAX_NUMBER_EXPRESSION);
		Matcher matcher = pattern.matcher(taxNumber);
		if (!matcher.matches()) {
			throw new InvalidException("Invalid tax number " + taxNumber, ErrorCodeEnum.INVALID_TAX_NUMBER);
		}
	}
}
