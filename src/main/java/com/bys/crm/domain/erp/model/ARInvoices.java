package com.bys.crm.domain.erp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ARInvoices")
public class ARInvoices extends DomainEntity<Long> {
	@Id
	@Column(name = "ARInvoiceID")
	private Long id;

	@Column(name = "ARInvoiceNo")
	private String invoiceNo;
	
	@Column(name = "AAStatus")
	private String status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARInvoiceDate")
	private DateTime invoiceDate;
	
	@Column(name = "ARInvoiceStatus")
	private String invoiceStatus;

	@Column(name = "ARInvoicesCoordinatorReference")
	private String coordinatorReference;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<ARInvoiceItems> items;

	@Column(name = "FK_ARCustomerID", columnDefinition = "int")
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

	public List<ARInvoiceItems> getItems() {
		return items;
	}

	public void setItems(List<ARInvoiceItems> items) {
		this.items = items;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
