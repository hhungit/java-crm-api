package com.bys.crm.app.dto;

import org.joda.time.DateTime;

public class DocumentDto {
	private Long id;

	private DateTime documentDate;

	private String documentNo;

	private String documentDesc;

	private Float documentTotalAmount;

	private Float documentExchangeAmount;

	private Long documentTypeId;

	private DateTime createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(DateTime documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDesc() {
		return documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public Float getDocumentTotalAmount() {
		return documentTotalAmount;
	}

	public void setDocumentTotalAmount(Float documentTotalAmount) {
		this.documentTotalAmount = documentTotalAmount;
	}

	public Float getDocumentExchangeAmount() {
		return documentExchangeAmount;
	}

	public void setDocumentExchangeAmount(Float documentExchangeAmount) {
		this.documentExchangeAmount = documentExchangeAmount;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

}