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
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADUserDevices")
public class ADUserDevices extends DomainEntity<Long>{

	@Id
	@Column(name = "ADUserDeviceID")
	private Long id;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime modifiedDate;
	
	@Column(name = "AAStatus")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="FK_ADUserID")
	private ADUsers user;
		
	@Column(name = "ADUserDeviceName")
	private String deviceKey;
	
	@Column(name = "ADUserDeviceType")
	private String deviceType;
	
	@Column(name = "ADUserDeviceIsActive")
	private Boolean active;
	
	public ADUserDevices() {
		this.createdDate = DateTime.now();
		this.modifiedDate = DateTime.now();
		this.status = AAStatus.Alive.name();
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ADUsers getUser() {
		return user;
	}

	public void setUser(ADUsers user) {
		this.user = user;
	}
		
}
