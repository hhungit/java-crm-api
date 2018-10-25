package com.bys.crm.app.dto;

public class OpportunityContactGroupDto {
	private Long id;

	private OpportunitySummaryDto opportunity;

	private CustomerContactSummaryDto contact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OpportunitySummaryDto getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(OpportunitySummaryDto opportunity) {
		this.opportunity = opportunity;
	}

	public CustomerContactSummaryDto getContact() {
		return contact;
	}

	public void setContact(CustomerContactSummaryDto contact) {
		this.contact = contact;
	}

}
