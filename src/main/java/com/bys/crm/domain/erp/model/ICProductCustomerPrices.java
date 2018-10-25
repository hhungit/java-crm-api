package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ICProductCustomerPrices")
public class ICProductCustomerPrices extends DomainEntity<Long> {
	@Id
	@Column(name = "ICProductCustomerPriceID", nullable = false)
	private Long id;

	@Column(name = "ICProductCustomerPriceProductUnitPrice")
	private String productUnitPrice;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ICProductCustomerPriceToDate")
	private DateTime priceToDate;

	@Column(name = "AAStatus")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ICProductID", columnDefinition = "int")
	private ICProducts product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ARCustomerID", columnDefinition = "int")
	private ARCustomers customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(String productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ICProducts getProduct() {
		return product;
	}

	public void setProduct(ICProducts product) {
		this.product = product;
	}

	public ARCustomers getCustomer() {
		return customer;
	}

	public void setCustomer(ARCustomers customer) {
		this.customer = customer;
	}

	public DateTime getPriceToDate() {
		return priceToDate;
	}

	public void setPriceToDate(DateTime priceToDate) {
		this.priceToDate = priceToDate;
	}

}
