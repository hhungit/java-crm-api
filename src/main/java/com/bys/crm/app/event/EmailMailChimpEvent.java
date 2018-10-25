package com.bys.crm.app.event;

import org.springframework.context.ApplicationEvent;

import com.ecwid.maleorang.method.v3_0.campaigns.CampaignInfo;

public class EmailMailChimpEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private CampaignInfo campaignInfo;
	
	public EmailMailChimpEvent(Object source, CampaignInfo campaign) {
		super(source);
		this.campaignInfo = campaign;
	}

	public CampaignInfo getCampaignInfo() {
		return campaignInfo;
	}

	public void setCampaignInfo(CampaignInfo campaignInfo) {
		this.campaignInfo = campaignInfo;
	}

}
