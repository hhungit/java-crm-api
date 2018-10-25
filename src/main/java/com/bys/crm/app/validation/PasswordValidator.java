package com.bys.crm.app.validation;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.ForgotPasswordDto;
import com.bys.crm.app.dto.PasswordDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.ADUsers;
import com.bys.crm.domain.erp.repository.ADUsersRepository;
import com.bys.crm.util.StringUtil;


@Component
public class PasswordValidator {

	public static final long EXPIRED_TIME = 1000 * 60 * 60 * 3;

	@Autowired
	private ADUsersRepository repository;

	@Resource
	private PasswordEncoder passwordEncoder;

	public void validate(ForgotPasswordDto requestDto) {
		List<ADUsers> users = repository.findByEmployeeEmailAndStatus(requestDto.getEmail(), AAStatus.Alive.value());
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("User with email not found");
		}
		requestDto.setUser(users.get(0));

	}

	public void validate(PasswordDto requestDto) {
		ADUsers user = null;
		// Reset password by token
		if (StringUtil.isNotEmpty(requestDto.getToken())) {
			user = repository.findByResetToken(requestDto.getToken());
		}  
		else {
			throw new InvalidException("Token required", ErrorCodeEnum.TOKEN_OR_USERNAME_REQUIRED);
		}

		if (user == null) {
			throw new InvalidException("User not found", ErrorCodeEnum.RESOURCE_NOT_FOUND);
		}

		if (DateTime.now().minus(PasswordValidator.EXPIRED_TIME).isAfter(user.getModifiedDate())) {
			throw new InvalidException("Pass code has expired", ErrorCodeEnum.PASS_CODE_EXPIRED);
		}

		validatePassword(requestDto.getNewPassword());
		requestDto.setUser(user);

	}

	public void validateChange(PasswordDto requestDto) {
		ADUsers user = null;
		if (StringUtil.isNotEmpty(requestDto.getUsername())) {
			String oldPassword = requestDto.getOldPassword();
			if (StringUtil.isEmpty(oldPassword)) {
				throw new InvalidException("Old password required", ErrorCodeEnum.MISS_OLD_PASSWORD);
			}

			user = repository.findByUsernameAndStatus(requestDto.getUsername(), AAStatus.Alive.value());
			if (user != null && !passwordEncoder.matches(oldPassword, user.getPassword())) {
				throw new InvalidException("Old password mismatch", ErrorCodeEnum.MISMATCH_OLD_PASSWORD);
			}
		}else {
			throw new InvalidException("Username required", ErrorCodeEnum.TOKEN_OR_USERNAME_REQUIRED);
		}
		if (user == null) {
			throw new InvalidException("User not found", ErrorCodeEnum.RESOURCE_NOT_FOUND);
		}
		validatePassword(requestDto.getNewPassword());
		requestDto.setUser(user);
	}

	public void validatePassword(String password) {
		if (password.length() < 6) {
			throw new InvalidException("Password has at least 6 characters",
					ErrorCodeEnum.PASSWORD_LENGTH_MIN_REQUIRED);
		}
	}

}
