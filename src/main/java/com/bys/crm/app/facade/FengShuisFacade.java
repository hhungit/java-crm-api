package com.bys.crm.app.facade;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.FengShuisColorDto;
import com.bys.crm.app.dto.FengShuisDirectionDto;
import com.bys.crm.app.dto.FengShuisDto;
import com.bys.crm.app.dto.FengShuisGenaralDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.ADFengShuisColors;
import com.bys.crm.domain.erp.model.ADFengShuisDirections;
import com.bys.crm.domain.erp.model.ADFengShuisGenarals;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.ADFengShuisColorsRepository;
import com.bys.crm.domain.erp.repository.ADFengShuisDirectionsRepository;
import com.bys.crm.domain.erp.repository.ADFengShuisGenaralsRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class FengShuisFacade {

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private ADFengShuisGenaralsRepository fengShuisGenaralsRepository;

	@Autowired
	private ADFengShuisDirectionsRepository fengShuisDirectionsRepository;

	@Autowired
	private ADFengShuisColorsRepository fengShuisColorsRepository;

	@Autowired
	private GenericMapper genericMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(FengShuisFacade.class);

	// Get employee list
	public FengShuisDto getFengShuisInfor(Integer employeeId, String lunarBirthday, String gender) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		if (StringUtils.isNotBlank(lunarBirthday)) {
			try {
				lunarBirthday = "%".concat(StringUtils.trim(URLDecoder.decode(lunarBirthday, "UTF-8"))).concat("%");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Decoder lunar birthday fail" + e);
			}
		}

		ADFengShuisGenarals fengShuisGenarals = fengShuisGenaralsRepository
				.findByStatusAndFengShuisGenaralBirthdateLike(AAStatus.Alive.name(),lunarBirthday);

		if (fengShuisGenarals == null) {
			throw new InvalidException("Not exist data with Lunar birthday is: " + lunarBirthday,
					ErrorCodeEnum.DATA_NOT_EXIST);
		}

		String fengShuisGenaral = "";
		if (gender.equalsIgnoreCase(Gender.Male.name())) {
			fengShuisGenaral = fengShuisGenarals.getFengShuisGenaralMale();
		} else if (gender.equalsIgnoreCase(Gender.Female.name())) {
			fengShuisGenaral = fengShuisGenarals.getFengShuisGenaralFemale();
		}

		String fengShuisDirectionMain = "%".concat(StringUtils.trim(fengShuisGenaral.split(" ")[0])).concat("%");
		String fengShuisColorLife = "%".concat(StringUtils.trim(fengShuisGenaral.split(" ")[1])).concat("%");

		ADFengShuisDirections fengShuisDirections = fengShuisDirectionsRepository
				.findByFengShuisDirectionMainLike(fengShuisDirectionMain);

		ADFengShuisColors fengShuisColors = fengShuisColorsRepository.findByFengShuisColorLifeLike(fengShuisColorLife);

		// Build DTO
		FengShuisGenaralDto fengShuisGenaralDto = genericMapper.buildObject(fengShuisGenarals,
				FengShuisGenaralDto.class);
		FengShuisDirectionDto fengShuisDirectionDto = genericMapper.buildObject(fengShuisDirections,
				FengShuisDirectionDto.class);
		FengShuisColorDto fengShuisColorDto = genericMapper.buildObject(fengShuisColors, FengShuisColorDto.class);

		return new FengShuisDto(fengShuisGenaralDto, fengShuisDirectionDto, fengShuisColorDto);
	}

	// Get Lunar Years list
	public List<String> getLunarYears(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<ADFengShuisGenarals> fengShuisGenarals = fengShuisGenaralsRepository.findByStatus(AAStatus.Alive.name());

		return fengShuisGenarals.stream().map(item -> {
			return item.getFengShuisGenaralBirthdate();
		}).collect(Collectors.toList());
	}
}
