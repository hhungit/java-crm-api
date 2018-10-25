package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class InvoiceDto {

	private Long id;

	private String invoiceNo;
	
	private String status;

	private DateTime invoiceDate;
	
	private String invoiceStatus;

	private String coordinatorReference;

	private Long customerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(DateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCoordinatorReference() {
		return coordinatorReference;
	}

	public void setCoordinatorReference(String coordinatorReference) {
		this.coordinatorReference = coordinatorReference;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}