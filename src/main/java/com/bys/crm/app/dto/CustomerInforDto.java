package com.bys.crm.app.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

public class CustomerInforDto {

	@Valid
	private List<ProductCustomerPriceDto> productCustomerPrices;

	@Valid
	private InvoiceItemDto invoiceLastest;
	
	@Valid
	private List<InvoiceItemDto> invoicesOfMonth;

	@Valid
	private BigDecimal customerBalance;

	public List<ProductCustomerPriceDto> getProductCustomerPrices() {
		return productCustomerPrices;
	}

	public void setProductCustomerPrices(List<ProductCustomerPriceDto> productCustomerPrices) {
		this.productCustomerPrices = productCustomerPrices;
	}

	public InvoiceItemDto getInvoiceLastest() {
		return invoiceLastest;
	}

	public void setInvoiceLastest(InvoiceItemDto invoiceLastest) {
		this.invoiceLastest = invoiceLastest;
	}

	public List<InvoiceItemDto> getInvoicesOfMonth() {
		return invoicesOfMonth;
	}

	public void setInvoicesOfMonth(List<InvoiceItemDto> invoicesOfMonth) {
		this.invoicesOfMonth = invoicesOfMonth;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}

	public CustomerInforDto(List<ProductCustomerPriceDto> productCustomerPrices, InvoiceItemDto invoiceLastest,
			List<InvoiceItemDto> invoicesOfMonth, BigDecimal customerBalance) {
		super();
		this.productCustomerPrices = productCustomerPrices;
		this.invoiceLastest = invoiceLastest;
		this.invoicesOfMonth = invoicesOfMonth;
		this.customerBalance = customerBalance;
	}

}