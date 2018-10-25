package com.bys.crm.app.dto;

public class ProductCustomerPriceDto {

	private Long id;

	private String productName;

	private String productNo;

	private String productUnitPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(String productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public ProductCustomerPriceDto(Long id, String productName, String productNo, String productUnitPrice) {
		super();
		this.id = id;
		this.productName = productName;
		this.productNo = productNo;
		this.productUnitPrice = productUnitPrice;
	}

}