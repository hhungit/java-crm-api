package com.bys.crm.app.dto;

public class DocumentEntryDto {
	private Long id;

	private String documentEntryDesc;

	private Long entryTypeId;

	private Long debitAccountId;

	private Long creditAccountId;

	private DocumentDto document;

	private Long objectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocumentEntryDesc() {
		return documentEntryDesc;
	}

	public void setDocumentEntryDesc(String documentEntryDesc) {
		this.documentEntryDesc = documentEntryDesc;
	}

	public Long getEntryTypeId() {
		return entryTypeId;
	}

	public void setEntryTypeId(Long entryTypeId) {
		this.entryTypeId = entryTypeId;
	}

	public Long getDebitAccountId() {
		return debitAccountId;
	}

	public void setDebitAccountId(Long debitAccountId) {
		this.debitAccountId = debitAccountId;
	}

	public Long getCreditAccountId() {
		return creditAccountId;
	}

	public void setCreditAccountId(Long creditAccountId) {
		this.creditAccountId = creditAccountId;
	}

	public DocumentDto getDocument() {
		return document;
	}

	public void setDocument(DocumentDto document) {
		this.document = document;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

}