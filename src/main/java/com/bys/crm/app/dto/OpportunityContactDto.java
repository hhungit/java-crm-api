package com.bys.crm.app.dto;

import javax.validation.Valid;

import org.joda.time.DateTime;

public class OpportunityContactDto {
	private Long id;

	private String createdUser;

	private DateTime createdDate;

	private String updatedUser;

	private DateTime updatedDate;

	@Valid
	private BranchSummaryDto branch;

	private Long opportunityId;

	@Valid
	private CustomerContactSummaryDto customerContact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public CustomerContactSummaryDto getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(CustomerContactSummaryDto customerContact) {
		this.customerContact = customerContact;
	}

}