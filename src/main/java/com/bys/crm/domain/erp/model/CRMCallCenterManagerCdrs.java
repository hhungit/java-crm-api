package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "CRMCallCenterManagerCdrs")
public class CRMCallCenterManagerCdrs extends DomainEntity<Long> {

	@Id
	@Column(name = "CRMCallCenterManagerCdrID")
	private Long id;

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

	@Column(name = "CRMCallCenterManagerCallStatus")
	private String callStatus;

	@Column(name = "CRMCallCenterManagerCallUuid")
	private String callUuid;

	@Column(name = "CRMCallCenterManagerDisposition")
	private String disposition;

	@Column(name = "CRMCallCenterManagerBillduration")
	private Float billduration;

	@Column(name = "CRMCallCenterManagerTotalduration")
	private Float totalduration;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "CRMCallCenterManagerStarttime")
	private DateTime starttime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "CRMCallCenterManagerEndtime")
	private DateTime endtime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "CRMCallCenterManagerAnswertime")
	private DateTime answertime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public String getCallUuid() {
		return callUuid;
	}

	public void setCallUuid(String callUuid) {
		this.callUuid = callUuid;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public Float getBillduration() {
		return billduration;
	}

	public void setBillduration(Float billduration) {
		this.billduration = billduration;
	}

	public Float getTotalduration() {
		return totalduration;
	}

	public void setTotalduration(Float totalduration) {
		this.totalduration = totalduration;
	}

	public DateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(DateTime starttime) {
		this.starttime = starttime;
	}

	public DateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(DateTime endtime) {
		this.endtime = endtime;
	}

	public DateTime getAnswertime() {
		return answertime;
	}

	public void setAnswertime(DateTime answertime) {
		this.answertime = answertime;
	}

	public CRMCallCenterManagerCdrs() {
		this.status = AAStatus.Alive.name();
		this.createdDate = DateTime.now();
		this.updatedDate = DateTime.now();
	}
}
