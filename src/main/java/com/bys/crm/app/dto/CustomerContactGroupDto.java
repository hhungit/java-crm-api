package com.bys.crm.app.dto;

public class CustomerContactGroupDto {
	private Long id;

	private CustomerSummaryDto customer;

	private CustomerContactSummaryDto contact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerSummaryDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSummaryDto customer) {
		this.customer = customer;
	}

	public CustomerContactSummaryDto getContact() {
		return contact;
	}

	public void setContact(CustomerContactSummaryDto contact) {
		this.contact = contact;
	}

}
