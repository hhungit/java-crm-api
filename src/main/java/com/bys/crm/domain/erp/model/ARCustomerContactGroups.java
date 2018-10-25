package com.bys.crm.domain.erp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Audited
@Table(name = "ARCustomerContactGroups")
public class ARCustomerContactGroups extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCustomerContactGroupID", nullable = false, columnDefinition = "int")
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ARCustomerID", columnDefinition = "int")
	private ARCustomers customer;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ARCustomerContactID", columnDefinition = "int")
	private ARCustomerContacts contact;
	
	public ARCustomerContactGroups() {
		this.status = AAStatus.Alive.name();
	}
	
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

	public ARCustomers getCustomer() {
		return customer;
	}

	public void setCustomer(ARCustomers customer) {
		this.customer = customer;
	}

	public ARCustomerContacts getContact() {
		return contact;
	}

	public void setContact(ARCustomerContacts contact) {
		this.contact = contact;
	}

}
