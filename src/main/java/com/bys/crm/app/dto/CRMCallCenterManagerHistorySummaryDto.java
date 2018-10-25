package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class CRMCallCenterManagerHistorySummaryDto {

	private String callUuid;	
	private String callernumber;
	private DateTime starttime;
	private String destinationnumber;
	
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
	public DateTime getStarttime() {
		return starttime;
	}
	public void setStarttime(DateTime starttime) {
		this.starttime = starttime;
	}
	public String getDestinationnumber() {
		return destinationnumber;
	}
	public void setDestinationnumber(String destinationnumber) {
		this.destinationnumber = destinationnumber;
	}
		
}
