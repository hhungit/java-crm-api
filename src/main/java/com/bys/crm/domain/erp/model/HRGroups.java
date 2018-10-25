package com.bys.crm.domain.erp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "HRGroups")
public class HRGroups extends DomainEntity<Integer> {
	@Id
	@Column(name = "HRGroupID", nullable = false)
	private Integer id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "HRGroupName", nullable = false, columnDefinition = "nvarchar(100)")
	private String name;

	@Column(name = "HRGroupDesc", columnDefinition = "nvarchar(200)")
	private String description;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "HREmployeeGroups", 
			joinColumns = @JoinColumn(name = "FK_HRGroupID"), 
			inverseJoinColumns = @JoinColumn(name = "FK_HREmployeeID"))
	private Set<HREmployees> employees;

	public HRGroups() {
		this.status = AAStatus.Alive.name();
	}

	public HRGroups(Integer id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<HREmployees> getEmployees() {
		if (employees == null) {
			employees = new HashSet<>();
		}
		return employees;
	}

	public void setEmployees(Set<HREmployees> employees) {
		this.employees = employees;
	}

}
