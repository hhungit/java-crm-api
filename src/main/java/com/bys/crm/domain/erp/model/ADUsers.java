package com.bys.crm.domain.erp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADUsers")
public class ADUsers extends DomainEntity<Long>{
	@Id
	@Column(name = "ADUserID")
	private Long id;
	
	@Column(name = "ADUserName", nullable = false)
	private String username;
	
	@Column(name = "ADPassword")
	private String password;
			
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime modifiedDate;
	
	@Column(name = "AAStatus")
	private String status;
	
//	@Column(name = "FK_HREmployeeID")
//	private Long employeeId;
	
	@Column(name = "ADUserActiveCheck")
	private boolean check;
	
	@Column(name = "ADUserResetToken", columnDefinition = "nvarchar(50)")
	private String resetToken;

	@Transient
	private ADUserDevices authenticatedDevice;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<ADUserDevices> devices;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;

	public ADUsers() {
		this.createdDate = DateTime.now();
		this.modifiedDate = DateTime.now();
		this.status = AAStatus.Alive.name();
//		this.type = ADObjectType.Customer.name();
		this.check = true;
	}
	
//	public ADPortalUsers(ARCustomers customer) {
//		this();
//		this.customer = customer;
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public ARCustomers getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(ARCustomers customer) {
//		this.customer = customer;
//	}

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Set<ADUserDevices> getDevices() {
		if (devices == null) {
			devices = new HashSet<>();
		}
		return devices;
	}

	public void setDevices(Set<ADUserDevices> devices) {
		this.devices = devices;
	}

	public ADUserDevices getAuthenticatedDevice() {
		return authenticatedDevice;
	}

	public void setAuthenticatedDevice(ADUserDevices authenticatedDevice) {
		this.authenticatedDevice = authenticatedDevice;
		this.getDevices().add(authenticatedDevice);
	}

	public HREmployees getEmployee() {
		return employee;
	}

	public void setEmployee(HREmployees employee) {
		this.employee = employee;
	}

//	public Long getEmployeeId() {
//		return employeeId;
//	}
//
//	public void setEmployeeId(Long employeeId) {
//		this.employeeId = employeeId;
//	}

		
}