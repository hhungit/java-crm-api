package com.bys.crm.api.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.exception.ResourceNotFoundException;
import com.ecwid.maleorang.MailchimpException;

public abstract class BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler(AccessDeniedException.class)
	public @ResponseBody ResponseDto handleException(AccessDeniedException exception,
			HttpServletResponse response, Object handler) {
		this.printException(exception, handler);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		ResponseDto error = new ResponseDto(ErrorCodeEnum.NOT_AUTHORIZE, exception.getMessage());
		
		return error;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody ResponseDto handleException(MethodArgumentNotValidException exception,
			HttpServletResponse response, Object handler) {

		this.printException(exception, handler);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		
		BindingResult bindingResult = exception.getBindingResult();
		// Get messages in annotation of dto object
		if (bindingResult != null && bindingResult.hasErrors()) {
			StringBuilder errorMessages = new StringBuilder();
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				String delimeter = (errorMessages.length() == 0) ? "" : ";";
				errorMessages.append(delimeter).append(fieldError.getDefaultMessage());
			});
 
			return new ResponseDto(ErrorCodeEnum.INVALID_REQUEST, errorMessages.toString());
		} else {
			return new ResponseDto(ErrorCodeEnum.INVALID_REQUEST,"Invalid Request");
		}
		

	}
	
	@ExceptionHandler(InvalidException.class)
	public @ResponseBody ResponseDto handleException(InvalidException e,
			HttpServletResponse response, Object handler) {
		
		this.printException(e, handler);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		ResponseDto error = new ResponseDto(e.getErrorCode(), e.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ResponseDto handleException(ResourceNotFoundException e,
			HttpServletResponse response, Object handler) {
		
		this.printException(e, handler);
		response.setStatus(HttpStatus.NOT_FOUND.value());
		ResponseDto error = new ResponseDto(e.getErrorCode(), e.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseDto handleException(Exception e,
			HttpServletResponse response, Object handler) {
		
		this.printException(e, handler);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		ResponseDto error = new ResponseDto(ErrorCodeEnum.INTERNAL_ERROR, "Internal error");
		
		return error;
	}
	
	@ExceptionHandler(MailSendException.class)
	public @ResponseBody ResponseDto handleException(MailSendException e,
			HttpServletResponse response, Object handler) {
		
		this.printException(e, handler);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		ResponseDto error = new ResponseDto(ErrorCodeEnum.SEND_EMAIL_ERROR, "Error sending email");
		
		return error;
	}
	
	@ExceptionHandler(MailchimpException.class)
	public @ResponseBody ResponseDto handleException(MailchimpException e,
			HttpServletResponse response, Object handler) {
		
		this.printException(e, handler);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		ResponseDto error = new ResponseDto(ErrorCodeEnum.SEND_EMAIL_ERROR, "Error sending email with mailchimp");
		
		return error;
	}

	private void printException(Exception exception, Object handler) {
		LOGGER.warn("ExceptionHandler | START:::: " + handler);
		LOGGER.warn(exception.getClass().getSimpleName() + "::: ", exception);
		LOGGER.warn("ExceptionHandler | END:::: ");
	}

}
