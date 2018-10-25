package com.bys.crm.app.dto;

import javax.validation.Valid;

import com.bys.crm.domain.erp.model.ICProducts;

public class InvoiceItemDto {

	private Long id;

	private String status;

	private String productName;
	
	private String productDesc;

	private String productSerialNo;
	
	private Float productQty;
	
	private Float productUnitPrice;
	
	private Float transferFeeAmount;
	
	private Float loadingAmount;
	
	private Float totalAmount;

	private ICProducts product;
	
	@Valid
	private InvoiceDto invoice;

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

	public InvoiceDto getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceDto invoice) {
		this.invoice = invoice;
	}

	public ICProducts getProduct() {
		return product;
	}

	public void setProduct(ICProducts product) {
		this.product = product;
	}

}