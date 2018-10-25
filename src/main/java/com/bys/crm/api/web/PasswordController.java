package com.bys.crm.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ForgotPasswordDto;
import com.bys.crm.app.dto.PasswordDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.PasswordFacade;


@RestController
public class PasswordController extends BaseController {

	@Autowired
	PasswordFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.FORGOT_PASSWORD)
	public ResponseDto forgotPassword(@RequestBody @Valid ForgotPasswordDto requestDto) {
		String token = facade.forgotPassword(requestDto);
		return new ResponseDto(token);
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.VALIDATE_RESET_PASSWORD)
	public ResponseDto validate(@RequestParam(required = true) String token) {
		//Boolean valid = facade.validate(token);
		return facade.validateNew(token);
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CHANGE_PASSWORD)
	public ResponseDto changePassword(@RequestBody @Valid PasswordDto requestDto, HttpServletRequest httpRequest) {
		return new ResponseDto(facade.changePassword(requestDto, httpRequest));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = RestURL.NEW_PASSWORD)
	public ResponseDto newPassword(@RequestBody @Valid PasswordDto requestDto, HttpServletRequest httpRequest) {
		return new ResponseDto(facade.newPassword(requestDto, httpRequest));
	}
}
