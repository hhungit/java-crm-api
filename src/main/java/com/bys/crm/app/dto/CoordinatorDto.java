package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class CoordinatorDto {

	private Long id;
	private String coordinatorNo;
	private String vehicleNoPlate;
	private DateTime coordinatorDate;
	private String coordinatorStatus;
	private EmployeeSummaryDto employee;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCoordinatorNo() {
		return coordinatorNo;
	}
	public void setCoordinatorNo(String coordinatorNo) {
		this.coordinatorNo = coordinatorNo;
	}
	public String getVehicleNoPlate() {
		return vehicleNoPlate;
	}
	public void setVehicleNoPlate(String vehicleNoPlate) {
		this.vehicleNoPlate = vehicleNoPlate;
	}
	public DateTime getCoordinatorDate() {
		return coordinatorDate;
	}
	public void setCoordinatorDate(DateTime coordinatorDate) {
		this.coordinatorDate = coordinatorDate;
	}
	public String getCoordinatorStatus() {
		return coordinatorStatus;
	}
	public void setCoordinatorStatus(String coordinatorStatus) {
		this.coordinatorStatus = coordinatorStatus;
	}
	public EmployeeSummaryDto getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}
	
}
