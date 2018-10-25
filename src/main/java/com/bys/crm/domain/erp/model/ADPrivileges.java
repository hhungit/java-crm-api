package com.bys.crm.domain.erp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ADPrivileges")
public class ADPrivileges extends DomainEntity<Integer> {

	@Id
	@Column(name = "ADPrivilegeID", nullable = false)
	private Integer id;

	@Column(name = "ADPrivilegeName", nullable = false, columnDefinition = "varchar(100)")
	private String name;

	@Column(name = "ADPrivilegeCaption", columnDefinition = "nvarchar(100)")
	private String caption;

	@Column(name = "AAStatus", columnDefinition = "varchar(50)")
	private String status;

	@Column(name = "FK_STModuleID")
	private Long stModuleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADPrivilegeGroupID")
	private ADPrivilegeGroups group;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "privilege", cascade = CascadeType.ALL)
	private List<ADPrivilegeDetails> details;

	public ADPrivileges() {
		this.status = AAStatus.Alive.name();
		this.stModuleId = Long.valueOf(1);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ADPrivilegeGroups getGroup() {
		return group;
	}

	public void setGroup(ADPrivilegeGroups group) {
		this.group = group;
	}

	public List<ADPrivilegeDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ADPrivilegeDetails> details) {
		this.details = details;
	}

	public Long getStModuleId() {
		return stModuleId;
	}

	public void setStModuleId(Long stModuleId) {
		this.stModuleId = stModuleId;
	}

}
