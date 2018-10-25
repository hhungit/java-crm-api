package com.bys.crm.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.NotificationFacade;

@RestController
public class NotificationController extends BaseController {
	@Autowired
	private NotificationFacade facade;

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_NOTIFICATIONS_BY_EMPLOYEE)
	public ResponseDto getNotificationsByEmployee(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getNotificationsByEmployee(employeeId, pageNumber, pageSize));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CHANGE_READ_NOTIFICATION)
	public ResponseDto changeReadNotification(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "notificationId") long notificationId) {
		return new ResponseDto(facade.changeReadNotification(employeeId, notificationId, true));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.COUNT_WAIT_NOTIFICATION)
	public ResponseDto countWaitNotification(@PathVariable(value = "employeeId") int employeeId) {
		return new ResponseDto(facade.countWaitNotification(employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_NOTIFICATIONS_BY_START_DATE)
	public ResponseDto getNotificationsByStartDate(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "startDate") Long startDate,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getNotificationsByStartDate(employeeId, startDate, pageNumber, pageSize));
	}
}
