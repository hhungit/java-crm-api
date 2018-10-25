package com.bys.crm.app.facade;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bys.crm.app.dto.ForgotPasswordDto;
import com.bys.crm.app.dto.PasswordDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.dto.UserInfoDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.event.ForgotPasswordEvent;
import com.bys.crm.app.validation.PasswordValidator;
import com.bys.crm.config.security.dto.UserLoginDto;
import com.bys.crm.config.security.filter.DeviceUsernamePasswordAuthenticationFilter;
import com.bys.crm.config.security.jwt.JWTService;
import com.bys.crm.domain.erp.model.ADUsers;
import com.bys.crm.domain.erp.repository.ADUsersRepository;

@Service
public class PasswordFacade {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ADUsersRepository repository;

	@Autowired
	private PasswordValidator validator;

	@Autowired
	private DeviceUsernamePasswordAuthenticationFilter filter;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;

	@Transactional
	public String forgotPassword(ForgotPasswordDto requestDto) {
		validator.validate(requestDto);
		ADUsers user = requestDto.getUser();
		user.setModifiedDate(DateTime.now());

		if (user.getResetToken() == null) {
			user.setResetToken(this.generateRandomCode(6));
		}
		repository.save(user);
		// Publish event to send email
		String token = user.getResetToken();
		ForgotPasswordEvent event = new ForgotPasswordEvent(this, requestDto.getEmail(), token);
		publisher.publishEvent(event);

		return user.getResetToken();
	}

	@Transactional
	public UserInfoDto changePassword(PasswordDto requestDto, HttpServletRequest httpRequest) {
		validator.validateChange(requestDto);
		ADUsers user = requestDto.getUser();
		user.setModifiedDate(DateTime.now());
		user.setResetToken(null);
		user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
		repository.save(user);
		UserLoginDto loginDto = new UserLoginDto(user.getUsername(), requestDto.getNewPassword(),
				requestDto.getDevice());

		filter.authenticate(httpRequest, loginDto);

		String token = jwtService.encode(loginDto.getLoginName());
		return new UserInfoDto(token, user);
	}

	@Transactional
	public UserInfoDto newPassword(PasswordDto requestDto, HttpServletRequest httpRequest) {
		validator.validate(requestDto);
		ADUsers user = requestDto.getUser();
		user.setModifiedDate(DateTime.now());
		user.setResetToken(null);
		user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
		repository.save(user);
		UserLoginDto loginDto = new UserLoginDto(user.getUsername(), requestDto.getNewPassword(),
				requestDto.getDevice());

		filter.authenticate(httpRequest, loginDto);

		String token = jwtService.encode(loginDto.getLoginName());
		return new UserInfoDto(token, user);
	}

	@Transactional
	public Boolean validate(String token) {
		ADUsers user = repository.findByResetToken(token);

		return user != null && DateTime.now().minus(PasswordValidator.EXPIRED_TIME).isBefore(user.getModifiedDate());
	}

	@Transactional
	public ResponseDto validateNew(String token) {
		ADUsers user = repository.findByResetToken(token);
		Boolean flag = false;
		ErrorCodeEnum messError = null;

		if (user != null && DateTime.now().minus(PasswordValidator.EXPIRED_TIME).isBefore(user.getModifiedDate()))
			flag = true;
		else {
			if (user == null)
				messError = ErrorCodeEnum.NOT_CORRECT_CODE;
			else
				messError = ErrorCodeEnum.EXPIRED_TIME;
		}

		return new ResponseDto(flag, messError);
	}

	private synchronized String generateRandomCode(int length) {
		boolean isExist = true;
		Random rand = new Random();
		StringBuilder builder = null;
		while (isExist) {
			builder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				int number = rand.nextInt(9) + 1;
				builder.append(String.valueOf(number));
			}
			isExist = repository.findByResetToken(builder.toString()) != null;
		}
		return builder.toString();
	}

}
