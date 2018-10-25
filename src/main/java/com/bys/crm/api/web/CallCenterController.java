package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CRMCallCenterManagerFacade;

@RestController
public class CallCenterController extends BaseController {

	@Autowired
	private CRMCallCenterManagerFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CALL_FROM_WORDFONE)
	public ResponseDto callFromWordFone(
			@RequestParam(value = "secret", required = true) String secret,
			@RequestParam(value = "callstatus", required = false) String callstatus, 
			@RequestParam(value = "calluuid", required = false) String calluuid,
			@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "callernumber", required = false) String callernumber,
			@RequestParam(value = "destinationnumber", required = false) String destinationnumber,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "dnis", required = false) String dnis,
			@RequestParam(value = "queue", required = false) String queue,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "calltype", required = false) String calltype,
			@RequestParam(value = "childcalluuid", required = false) String childcalluuid,
			@RequestParam(value = "datereceived", required = false) String datereceived,
			@RequestParam(value = "causetxt", required = false) String causetxt,
			@RequestParam(value = "answertime", required = false) String answertime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "billduration", required = false) Float billduration,
			@RequestParam(value = "totalduration", required = false) Float totalduration,
			@RequestParam(value = "disposition", required = false) String disposition,
			@RequestParam(value = "parentcalluuid", required = false) String parentCallUuid,
			@RequestParam(value = "calluuids", required = false) String calluuids) {
		return new ResponseDto(facade.callCenterManager(secret, callstatus, calluuid, direction, callernumber,
				destinationnumber, starttime, dnis, queue, version, calltype, childcalluuid, datereceived, causetxt,
				answertime, endtime, billduration, totalduration, disposition, parentCallUuid, calluuids));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CURRENT_CALL)
	public ResponseDto managerCalls(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.managerCalls(employeeId));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CALL_OUTBOUND)
	public ResponseDto getCallOutbound(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.getCallOutbound(employeeId));
	}
}
