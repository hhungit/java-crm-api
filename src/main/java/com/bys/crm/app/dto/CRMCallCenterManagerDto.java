package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class CRMCallCenterManagerDto {

	private String callStatus;
	
	private String callUuid;
	
	private String direction;
	
	private String callernumber;
	
	private String destinationnumber;
	
	private String dnis;
	
	private String queue;
	
	private String calltype;
	
	private String childcalluuid;
	
	private String parentcalluuid;
	
	private String causetxt;
	
	private String disposition;
	
	private Float billduration;
	
	private Float totalduration;

	private DateTime starttime;
	
	private DateTime endtime;
	
	private DateTime datereceived;
	
	private DateTime answertime;
	
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

	public String getParentcalluuid() {
		return parentcalluuid;
	}

	public void setParentcalluuid(String parentcalluuid) {
		this.parentcalluuid = parentcalluuid;
	}
	
}
