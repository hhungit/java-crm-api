package com.bys.crm.app.dto;

public class CRMCallCenterManagerSummaryDto {

	private String callStatus;
	private String callUuid;	
	private String callernumber;
	private String destinationnumber;
	private String direction;
	
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
