package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ACDocumentEntrys")
public class ACDocumentEntrys extends DomainEntity<Long> {
	@Id
	@Column(name = "ACDocumentEntryID", nullable = false)
	private Long id;

	@Column(name = "ACDocumentEntryDesc")
	private String documentEntryDesc;

	@Column(name = "AAStatus")
	private String status;

	@Column(name = "FK_ACEntryTypeID")
	private Long entryTypeId;

	@Column(name = "FK_ACDebitAccountID")
	private Long debitAccountId;

	@Column(name = "FK_ACCreditAccountID")
	private Long creditAccountId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACDocumentID")
	private ACDocuments document;

	@Column(name = "FK_ACObjectID")
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public ACDocuments getDocument() {
		return document;
	}

	public void setDocument(ACDocuments document) {
		this.document = document;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

}
