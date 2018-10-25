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
@Table(name = "ARInvoiceItems")
public class ARInvoiceItems extends DomainEntity<Long> {
	@Id
	@Column(name = "ARInvoiceItemID")
	private Long id;

	@Column(name = "AAStatus")
	private String status;

	@Column(name = "ARInvoiceItemProductName")
	private String productName;
	
	@Column(name = "ARInvoiceItemProductDesc")
	private String productDesc;

	@Column(name = "ARInvoiceItemProductSerialNo")
	private String productSerialNo;
	
	@Column(name = "ARInvoiceItemProductQty")
	private Float productQty;
	
	@Column(name = "ARInvoiceItemProductUnitPrice")
	private Float productUnitPrice;
	
	@Column(name = "ARInvoiceItemTransferFeeAmount")
	private Float transferFeeAmount;
	
	@Column(name = "ARInvoiceItemLoadingAmount")
	private Float loadingAmount;
	
	@Column(name = "ARInvoiceItemTotalAmount")
	private Float totalAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARInvoiceID")
	private ARInvoices invoice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ICProductID", columnDefinition = "int")
	private ICProducts product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductSerialNo() {
		return productSerialNo;
	}

	public void setProductSerialNo(String productSerialNo) {
		this.productSerialNo = productSerialNo;
	}

	public Float getProductQty() {
		return productQty;
	}

	public void setProductQty(Float productQty) {
		this.productQty = productQty;
	}

	public Float getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(Float productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public Float getTransferFeeAmount() {
		return transferFeeAmount;
	}

	public void setTransferFeeAmount(Float transferFeeAmount) {
		this.transferFeeAmount = transferFeeAmount;
	}

	public Float getLoadingAmount() {
		return loadingAmount;
	}

	public void setLoadingAmount(Float loadingAmount) {
		this.loadingAmount = loadingAmount;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ARInvoices getInvoice() {
		return invoice;
	}

	public void setInvoice(ARInvoices invoice) {
		this.invoice = invoice;
	}

	public ICProducts getProduct() {
		return product;
	}

	public void setProduct(ICProducts product) {
		this.product = product;
	}

}
