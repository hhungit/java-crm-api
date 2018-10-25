package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.CountryDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CountryFacade;

@RestController
public class CountryController extends BaseController {
	@Autowired
	private CountryFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_COUNTRY)
	public ResponseDto createCountry(@PathVariable(value = "employeeId") int employeeId, @RequestBody @Valid CountryDto dto) {
		return new ResponseDto(facade.createCountry(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COUNTRY_BY_ID)
	public ResponseDto getCountryById(@PathVariable(value = "employeeId") int employeeId,
									  @PathVariable(value = "countryId") long countryId) {
		return new ResponseDto(facade.getCountryById(employeeId, countryId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_COUNTRY)
	public ResponseDto editCountry(@PathVariable(value = "employeeId") int employeeId,
								   @RequestBody @Valid CountryDto dto) {
		return new ResponseDto(facade.editCountry(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_COUNTRY)
	public ResponseDto deleteCountry(@PathVariable(value = "employeeId") int employeeId,
									 @PathVariable(value = "countryId") long coutryId) {
		return new ResponseDto(facade.deleteCountry(coutryId, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COUNTRY_LIST)
	public ResponseDto getCountryList(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getCountryList(employeeId));
	}

}
