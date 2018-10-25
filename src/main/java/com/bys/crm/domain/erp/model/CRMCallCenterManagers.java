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
@Table(name = "CRMCallCenterManagers")
public class CRMCallCenterManagers extends DomainEntity<Long> {

	@Id
	@Column(name = "CRMCallCenterManagerID")
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

	@Column(name = "CRMCallCenterManagerDirection")
	private String direction;

	@Column(name = "CRMCallCenterManagerCallernumber")
	private String callernumber;

	@Column(name = "CRMCallCenterManagerDestinationnumber")
	private String destinationnumber;

	@Column(name = "CRMCallCenterManagerDnis")
	private String dnis;

	@Column(name = "CRMCallCenterManagerQueue")
	private String queue;

	@Column(name = "CRMCallCenterManagerCalltype")
	private String calltype;

	@Column(name = "CRMCallCenterManagerChildcalluuid")
	private String childcalluuid;

	@Column(name = "CRMCallCenterManagerParentcalluuid")
	private String parentcalluuid;
	
	@Column(name = "CRMCallCenterManagerCausetxt")
	private String causetxt;

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
	@Column(name = "CRMCallCenterManagerDatereceived")
	private DateTime datereceived;

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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getCallernumber() {
		return callernumber;
	}

	public void setCallernumber(String callernumber) {
		this.callernumber = callernumber;
	}

	public String getDestinationnumber() {
		return destinationnumber;
	}

	public void setDestinationnumber(String destinationnumber) {
		this.destinationnumber = destinationnumber;
	}

	public String getDnis() {
		return dnis;
	}

	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getCalltype() {
		return calltype;
	}

	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}

	public String getChildcalluuid() {
		return childcalluuid;
	}

	public void setChildcalluuid(String childcalluuid) {
		this.childcalluuid = childcalluuid;
	}

	public String getCausetxt() {
		return causetxt;
	}

	public void setCausetxt(String causetxt) {
		this.causetxt = causetxt;
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

	public DateTime getDatereceived() {
		return datereceived;
	}

	public void setDatereceived(DateTime datereceived) {
		this.datereceived = datereceived;
	}

	public DateTime getAnswertime() {
		return answertime;
	}

	public void setAnswertime(DateTime answertime) {
		this.answertime = answertime;
	}

	public CRMCallCenterManagers() {
		this.status = AAStatus.Alive.name();
		this.createdDate = DateTime.now();
		this.updatedDate = DateTime.now();
	}
}
