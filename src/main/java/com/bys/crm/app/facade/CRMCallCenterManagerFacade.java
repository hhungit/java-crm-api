package com.bys.crm.app.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bys.crm.app.dto.CRMCallCenterManagerDto;
import com.bys.crm.app.dto.CRMCallCenterManagerHistorySummaryDto;
import com.bys.crm.app.dto.CRMCallCenterManagerSummaryDto;
import com.bys.crm.app.dto.ManagerCallDto;
import com.bys.crm.app.dto.StatusMessengerEnum;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.CRMCallCenterManagerCdrMapper;
import com.bys.crm.app.mapping.CRMCallCenterManagerMapper;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.CRMCallCenterManagerCdrs;
import com.bys.crm.domain.erp.model.CRMCallCenterManagers;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.repository.CRMCallCenterManagerCdrsRepository;
import com.bys.crm.domain.erp.repository.CRMCallCenterManagersRepository;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;

@Service
public class CRMCallCenterManagerFacade {

	@Autowired
	private CRMCallCenterManagerMapper mapper;

	@Autowired
	private CRMCallCenterManagerCdrMapper cdrMapper;

	@Autowired
	private CRMCallCenterManagersRepository repository;

	@Autowired
	private CRMCallCenterManagerCdrsRepository cdrRepository;

	@Autowired
	private HREmployeesRepository employeesRepository;

	@Autowired
	private GenericMapper genericMapper;

	private static final String START_STATUS = "Start";
	private static final String DIALING_STATUS = "Dialing";
	private static final String DIAL_ANSWER_STATUS = "DialAnswer";
	private static final String HANGUP_STATUS = "HangUp";
	private static final String CDR_STATUS = "CDR";
	private static final String TRIM_STATUS = "Trim";
	private static final String SYNC_CUR_CALLS_STATUS = "SyncCurCalls";

	private static final Logger LOGGER = LoggerFactory.getLogger(CRMCallCenterManagerFacade.class);

	@Transactional
	public String callCenterManager(String secret, String callStatus, String callUuid, String direction,
			String callerNumber, String destinationNumber, String starttime, String dnis, String queue, String version,
			String callType, String childCallUuid, String datereceived, String causetxt, String answertime,
			String endtime, Float billDuration, Float totalDuration, String disposition, String parentCallUuid,
			String calluuids) {

		LOGGER.info("callCenterManager secret" + secret + " callStatus " + callStatus + " callUuid " + callUuid
				+ " direction " + direction + " callerNumber " + callerNumber + " destinationNumber "
				+ destinationNumber + " starttime " + starttime + " dnis " + dnis + " queue " + queue + " version "
				+ version + " callType " + callType + " childCallUuid " + childCallUuid + " datereceived "
				+ datereceived + " causetxt " + causetxt + " answertime " + answertime + " endtime " + endtime
				+ " billDuration " + billDuration + " totalDuration " + totalDuration + " disposition " + disposition
				+ " parentCallUuid " + parentCallUuid + " calluuids " + calluuids);

		// check secret
		if (!"52cb7e9b9b0d23462f6d8c24b3cc27ad".equals(secret)) {
			throw new InvalidException("Invalid secret key .", ErrorCodeEnum.NOT_AUTHORIZE);
		}

		CRMCallCenterManagerDto callCenterManagerDto = null;

		// check status
		switch (callStatus) {
		case START_STATUS:
			callCenterManagerDto = setCRMCallCenterManagerDtoInCaseStart(callStatus, callUuid, direction, callerNumber,
					destinationNumber, starttime, dnis, queue);
			break;
		case DIALING_STATUS:
			callCenterManagerDto = setCRMCallCenterManagerDtoInCaseDialing(callStatus, callUuid, direction,
					callerNumber, destinationNumber, callType, starttime, dnis, parentCallUuid);
			break;
		case DIAL_ANSWER_STATUS:
			callCenterManagerDto = setCRMCallCenterManagerDtoInCaseDialAnswer(callStatus, callUuid, callerNumber,
					destinationNumber, childCallUuid);
			break;
		case HANGUP_STATUS:
			callCenterManagerDto = setCRMCallCenterManagerDtoInCaseHangUp(callStatus, callUuid, datereceived, causetxt);
			break;
		case TRIM_STATUS:
			//callCenterManagerDto = setCRMCallCenterManagerDtoInCaseTrim(callStatus, callUuid);
			this.updateStatusCall(callUuid);
			return StatusMessengerEnum.Successful.name();
		case CDR_STATUS:
			callCenterManagerDto = setCRMCallCenterManagerDtoInCaseCDR(callStatus, callUuid, starttime, answertime,
					endtime, billDuration, totalDuration, disposition);
			// Save info from WordFone into DB
			saveInfoCDRFromWordFone(callCenterManagerDto);
			this.updateStatusCall(callUuid);
			return StatusMessengerEnum.Successful.name();
		case SYNC_CUR_CALLS_STATUS:
			//callCenterManagerDto = setCRMCallCenterManagerDtoInCaseTrim(callStatus, callUuid);
			this.updateStatusCalInCaseSyncCurrCalls(calluuids);
			return StatusMessengerEnum.Successful.name();
		default:
			break;
		}
		// Save info from WordFone into DB
		saveInfoFromWordFone(callCenterManagerDto);

		return StatusMessengerEnum.Successful.name();
	}

	private void saveInfoFromWordFone(CRMCallCenterManagerDto dto) {

		CRMCallCenterManagers entity = mapper.buildEntity(dto);

		entity.setStarttime(DateTime.now());
		entity.setEndtime(DateTime.now());
		entity.setAnswertime(DateTime.now());
		entity.setDatereceived(DateTime.now());

		repository.save(entity);
	}

	// Save CDR
	private void saveInfoCDRFromWordFone(CRMCallCenterManagerDto dto) {

		CRMCallCenterManagerCdrs entity = cdrMapper.buildEntity(dto);

		entity.setStarttime(DateTime.now());
		entity.setEndtime(DateTime.now());
		entity.setAnswertime(DateTime.now());

		cdrRepository.save(entity);
	}

	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseStart(String callStatus, String callUuid,
			String direction, String callerNumber, String destinationNumber, String starttime, String dnis,
			String queue) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		dto.setDirection(direction);
		dto.setCallernumber(callerNumber);
		dto.setDestinationnumber(destinationNumber);
		// dto.setStarttime(starttime);
		dto.setDnis(dnis);
		dto.setQueue(queue);
		return dto;
	}

	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseDialing(String callStatus, String callUuid,
			String direction, String callerNumber, String destinationNumber, String callType, String starttime,
			String dnis, String parentCallUuid) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		dto.setDirection(direction);
		dto.setCallernumber(callerNumber);
		dto.setDestinationnumber(destinationNumber);
		dto.setCalltype(callType);
		// dto.setStarttime(starttime);
		dto.setDnis(dnis);
		dto.setParentcalluuid(parentCallUuid);
		return dto;
	}

	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseDialAnswer(String callStatus, String callUuid,
			String callerNumber, String destinationNumber, String childCallUuid) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		dto.setCallernumber(callerNumber);
		dto.setDestinationnumber(destinationNumber);
		dto.setChildcalluuid(childCallUuid);
		return dto;
	}

	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseHangUp(String callStatus, String callUuid,
			String datereceived, String causetxt) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		// dto.setDatereceived(datereceived);
		dto.setCausetxt(causetxt);
		return dto;
	}

	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseCDR(String callStatus, String callUuid,
			String starttime, String answertime, String endtime, Float billDuration, Float totalDuration,
			String disposition) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		// dto.setStarttime(starttime);
		// dto.setAnswertime(answertime);
		// dto.setEndtime(endtime);
		dto.setBillduration(billDuration);
		dto.setTotalduration(totalDuration);
		dto.setDisposition(disposition);
		return dto;
	}

	// wait
	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseTrim(String callStatus, String callUuid) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		return dto;
	}

	// wait
	private CRMCallCenterManagerDto setCRMCallCenterManagerDtoInCaseSyncCurCalls(String callStatus, String callUuid) {
		CRMCallCenterManagerDto dto = new CRMCallCenterManagerDto();
		dto.setCallStatus(callStatus);
		dto.setCallUuid(callUuid);
		return dto;
	}
	
	@Transactional
	public List<CRMCallCenterManagerHistorySummaryDto> getCallOutbound(Integer employeeId){
		List<CRMCallCenterManagerHistorySummaryDto> historyCalls = new ArrayList<>();
		historyCalls = this.getHistoryCallOutbound(0, 5);
		return historyCalls;
	}

	@Transactional
	public ManagerCallDto managerCalls(Integer employeeId){
		ManagerCallDto dto = new ManagerCallDto();
		
		List<CRMCallCenterManagerSummaryDto> currentCalls = this.getCurrentCall(employeeId);
		dto.setCurrentCalls(currentCalls);
		
		List<CRMCallCenterManagerHistorySummaryDto> historyCalls = this.getHistoryCall(0, 5);
		dto.setHistoryCalls(historyCalls);
		
		return dto;
	}
	
	private List<CRMCallCenterManagerSummaryDto> getCurrentCall(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<String> callStatus = Arrays.asList("Start", "DialAnswer", "HangUp");

		List<CRMCallCenterManagers> entities = repository.findByStatusAndCallStatusIn(AAStatus.ALive.name(),
				callStatus);
			
		return entities.stream().map(entity -> this.genericMapper.buildObject(entity, CRMCallCenterManagerSummaryDto.class))
				.collect(Collectors.toList());
	}
	
	private List<CRMCallCenterManagerHistorySummaryDto> getHistoryCall(Integer pageNumber, Integer pageSize){
					
		Pageable callPageRequest = new PageRequest(pageNumber, pageSize);		
	
		Page<CRMCallCenterManagers> entities = repository.findHistoryCalled(callPageRequest);
				
		List<CRMCallCenterManagerHistorySummaryDto> dtos = null;
		if (entities != null && entities.getContent() != null
				&& !entities.getContent().isEmpty()) {
			dtos = entities.getContent().stream().map(entity -> this.genericMapper.buildObject(entity, CRMCallCenterManagerHistorySummaryDto.class))
					.collect(Collectors.toList());
		}
		return dtos;
	}
	
	private List<CRMCallCenterManagerHistorySummaryDto> getHistoryCallOutbound(Integer pageNumber, Integer pageSize){
		
		Pageable callPageRequest = new PageRequest(pageNumber, pageSize,new Sort(Direction.DESC, "createdDate"));		
	
		Page<CRMCallCenterManagers> entities = repository.findByStatusAndDirection(AAStatus.Delete.name(),"outbound",callPageRequest);
				
		List<CRMCallCenterManagerHistorySummaryDto> dtos = null;
		if (entities != null && entities.getContent() != null
				&& !entities.getContent().isEmpty()) {
			dtos = entities.getContent().stream().map(entity -> this.genericMapper.buildObject(entity, CRMCallCenterManagerHistorySummaryDto.class))
					.collect(Collectors.toList());
		}
		return dtos;
	}
	
	private void updateStatusCall(String uuid){
		LOGGER.info("updateStatusCall " + uuid);
		List<CRMCallCenterManagers> entities = repository.findByStatusAndCallUuid(AAStatus.Alive.name(), uuid);
		if(entities!=null)
			entities.stream().forEach(entity -> entity.setStatus(AAStatus.Delete.name()));
		repository.save(entities);		
	}
	
	private void updateStatusCalInCaseSyncCurrCalls(String uuids){
		LOGGER.info("updateStatusCalInCaseSyncCurrCalls " + uuids);
		List<String> uuid = Arrays.asList(uuids.split(","));
		List<CRMCallCenterManagers> entities = repository.findByStatusAndCallUuidNotIn(AAStatus.Alive.name(), uuid);
		if(entities!=null)
			entities.stream().forEach(entity -> entity.setStatus(AAStatus.Delete.name()));
		repository.save(entities);
	}
}
