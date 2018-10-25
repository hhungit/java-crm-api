package com.bys.crm.api.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bys.crm.app.dto.ActivityDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.ActivityFacade;

@RestController
public class ActivityController extends BaseController {
	@Autowired
	private ActivityFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_ACTIVITY)
	public ResponseDto createActivity(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid ActivityDto dto) {
		return new ResponseDto(facade.createActivity(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_ACTIVITY_BY_ID)
	public ResponseDto getActivityById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "activityType") String activityType,
			@PathVariable(value = "activityId") long activityId) {
		return new ResponseDto(facade.getActivityById(employeeId, activityId, activityType));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_ACTIVITY)
	public ResponseDto editActivity(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "activityType") String activityType, @RequestBody @Valid ActivityDto dto) {
		return new ResponseDto(facade.editActivity(dto, employeeId, activityType));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_ACTIVITY)
	public ResponseDto deleteActivity(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "activityType") String activityType,
			@PathVariable(value = "activityId") long activityId) {
		return new ResponseDto(facade.deleteActivity(activityId, employeeId, activityType));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_ACTIVITY_LIST)
	public ResponseDto deleteActivityList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<ActivityDto> dtos) {
		return new ResponseDto(facade.deleteActivityList(dtos, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_ACTIVITY)
	public ResponseDto searchActivity(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchActivity(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.IMPORT_ACTIVITYS_FROM_EXCEL)
	public ResponseDto importActivityFromExcel(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "filePath") MultipartFile file) {
		return new ResponseDto(facade.importActivityFromExcel(employeeId, file));
	}

//	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_ACTIVITY_BY_EMPLOYEE)
//	public ResponseDto getActivityByEmployee(@PathVariable(value = "employeeId") int employeeId,
//			@PathVariable(value = "activityType") String activityType) {
//		return new ResponseDto(
//				facade.getActivityByEmployee(employeeId, ));
//	}activityType

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_ACTIVITY_BY_OBJECT_TYPE)
	public ResponseDto getActivityByObjectType(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "objectType") String objectType, @PathVariable(value = "objectId") long objectId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(
				facade.getActivityByObjectType(employeeId, objectType, objectId, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_ACTIVITY_BY_EMPLOYEE_AND_TYPE)
	public ResponseDto getActivityByEmployee(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "activityType") String activityType,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(
				facade.getActivityByEmployeeAndType(employeeId, activityType, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_ACTIVITY)
	public ResponseDto activityFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "activityType", required = false) String activityType,
			@RequestParam(value = "activityStatus", required = false) String activityStatus,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "createdUserId", required = false) Integer createdUserId,
			@RequestParam(value = "assignmentEmployeeId", required = false) Integer assignmentEmployeeId,
			@RequestParam(value = "assignmentEmployeeGroupId", required = false) Integer assignmentEmployeeGroupId
			){
		return new ResponseDto(facade.activityFilter(employeeId, searchKey, activityType, activityStatus, fromDate,
				toDate, pageNumber, pageSize, sortBy, direct, createdUserId,assignmentEmployeeId,assignmentEmployeeGroupId));
	}
}
