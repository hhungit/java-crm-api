package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ACDocuments")
public class ACDocuments extends DomainEntity<Long> {
	@Id
	@Column(name = "ACDocumentID", nullable = false)
	private Long id;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ACDocumentDate")
	private DateTime documentDate;

	@Column(name = "ACDocumentNo")
	private String documentNo;

	@Column(name = "ACDocumentDesc")
	private String documentDesc;

	@Column(name = "ACDocumentTotalAmount")
	private Float documentTotalAmount;

	@Column(name = "ACDocumentExchangeAmount")
	private Float documentExchangeAmount;

	@Column(name = "AAStatus")
	private String status;

	@Column(name = "FK_ACDocumentTypeID")
	private Long documentTypeId;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
