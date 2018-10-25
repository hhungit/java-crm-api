package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.AuditFacade;

@RestController
public class AuditController extends BaseController {
	@Autowired
	private AuditFacade facade; 

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_AUDIT_INFO)
	public ResponseDto getAuditInfoById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "objectType") String objectType,
			@PathVariable(value = "objectId") long objectId,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getAuditInfoById(employeeId, objectType, objectId, pageNumber, pageSize));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_AUDIT_INFO_BY_USER)
	public ResponseDto getAuditInfoByUser(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getAuditInfoByUser(employeeId, pageNumber, pageSize));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_AUDIT_INFO_BY_USER_WITH_RECORD_NUMBER)
	public ResponseDto getAuditInfoByUserWithRecordNumber(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "recordNumber") long recordNumber) {
		return new ResponseDto(facade.getAuditInfoByUser(employeeId, recordNumber));
	}
}
