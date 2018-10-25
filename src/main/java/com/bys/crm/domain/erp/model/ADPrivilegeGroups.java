package com.bys.crm.domain.erp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADPrivilegeGroups")
public class ADPrivilegeGroups extends DomainEntity<Integer>{
	@Id
	@Column(name = "ADPrivilegeGroupID", nullable = false)
	private Integer id;

	@Column(name = "AAStatus", columnDefinition = "varchar(50)")
	private String status;

	@Column(name = "ADPrivilegeGroupName", nullable = false, columnDefinition = "nvarchar(50)")
	private String name;

	@Column(name = "ADPrivilegeGroupDesc", columnDefinition = "nvarchar(512)")
	private String description;

//	@ManyToMany(mappedBy = "privilegeGroups", cascade = CascadeType.ALL)
//	private Set<HREmployees> employees;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
	private List<ADPrivileges> privileges;

	public ADPrivilegeGroups(){
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<HREmployees> getEmployees() {
//		if (employees == null) {
//			employees = new HashSet<>();
//		}
//		return employees;
//	}
//
//	public void setEmployees(Set<HREmployees> employees) {
//		this.employees = employees;
//	}

	public List<ADPrivileges> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<ADPrivileges> privileges) {
		this.privileges = privileges;
	}

}
