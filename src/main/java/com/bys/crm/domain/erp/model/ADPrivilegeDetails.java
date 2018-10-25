package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADPrivilegeDetails")
public class ADPrivilegeDetails extends DomainEntity<Integer>{
	@Id
	@Column(name = "ARPrivilegeDetailID", nullable = false)
	private Integer id;

	@Column(name = "AAStatus", columnDefinition = "varchar(50)")
	private String status;

	@Column(name = "ARPrivilegeDetailName", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@Column(name = "ARPrivilegeDetailValue", columnDefinition = "varchar(200)")
	private String value;
	
	@Column(name = "ARPrivilegeDetailDesc", columnDefinition = "nvarchar(512)")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ADPrivilegeID")
	private ADPrivileges privilege;

	public ADPrivilegeDetails(){
		this.status = AAStatus.Alive.name();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String nalue) {
		this.value = nalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ADPrivileges getPrivilege() {
		return privilege;
	}

	public void setPrivilege(ADPrivileges privilege) {
		this.privilege = privilege;
	}

}
