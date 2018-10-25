package com.bys.crm.app.dto;

import java.util.List;

public class ManagerCallDto {
	
	private List<CRMCallCenterManagerSummaryDto> currentCalls;
	
	private List<CRMCallCenterManagerHistorySummaryDto> historyCalls;

	public List<CRMCallCenterManagerSummaryDto> getCurrentCalls() {
		return currentCalls;
	}

	public void setCurrentCalls(List<CRMCallCenterManagerSummaryDto> currentCalls) {
		this.currentCalls = currentCalls;
	}

	public List<CRMCallCenterManagerHistorySummaryDto> getHistoryCalls() {
		return historyCalls;
	}

	public void setHistoryCalls(List<CRMCallCenterManagerHistorySummaryDto> historyCalls) {
		this.historyCalls = historyCalls;
	}
	
	

}
