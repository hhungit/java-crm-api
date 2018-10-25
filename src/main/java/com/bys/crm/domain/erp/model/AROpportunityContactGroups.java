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
@Table(name = "AROpportunityContactGroups")
public class AROpportunityContactGroups extends DomainEntity<Long> {
	@Id
	@Column(name = "AROpportunityContactGroupID", nullable = false, columnDefinition = "int")
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_AROpportunityID")
	private AROpportunitys opportunity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ARCustomerContactID", columnDefinition = "int")
	private ARCustomerContacts contact;
	
	public AROpportunityContactGroups() {
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

	public AROpportunitys getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(AROpportunitys opportunity) {
		this.opportunity = opportunity;
	}

	public ARCustomerContacts getContact() {
		return contact;
	}

	public void setContact(ARCustomerContacts contact) {
		this.contact = contact;
	}

}
