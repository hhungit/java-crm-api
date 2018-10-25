package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ARCoordinators")
public class ARCoordinators extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCoordinatorID")
	private Long id;

	@Column(name = "ARCoordinatorNo")
	private String coordinatorNo;
	
	@Column(name = "AAStatus")
	private String status;

	@Column(name = "AACreatedUser")
	private String createdUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;

	@Column(name = "AAUpdatedUser")
	private String updatedUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;
	
	@Column(name = "ARCoordinatorVehicleNoPlate")
	private String vehicleNoPlate;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCoordinatorDate")
	private DateTime coordinatorDate;
	
	@Column(name = "ARCoordinatorStatus")
	private String coordinatorStatus;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public HREmployees getEmployee() {
		return employee;
	}

	public void setEmployee(HREmployees employee) {
		this.employee = employee;
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
}
