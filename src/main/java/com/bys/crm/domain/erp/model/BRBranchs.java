package com.bys.crm.domain.erp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "BRBranchs")
public class BRBranchs extends DomainEntity<Integer> {
	@Id
	@Column(name = "BRBranchID")
	private Integer id;

	@Column(name = "BRBranchName")
	private String name;

	@Column(name = "BRBranchNo")
	private String branchNumber;

	@Column(name = "BRBranchDesc")
	private String description;

	@Column(name = "BRBranchType")
	private String type;

	@Column(name = "BRBranchActiveCheck")
	private Boolean check;

	@Column(name = "BRBranchParentID")
	private Long parentId;

	@Column(name = "BRBranchBankCode")
	private String bankCode;

	@Column(name = "BRBranchBankName")
	private String bankName;

	@Column(name = "BRBranchTaxNumber")
	private String tax;

	@Column(name = "BRBranchContactLevel")
	private String contactLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_GELocationID")
	private GELocations location;

	@Column(name = "AAStatus")
	private String status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime modifiedDate;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "HREmployeeGroups", 
			joinColumns = @JoinColumn(name = "FK_BRBranchID"), 
			inverseJoinColumns = @JoinColumn(name = "FK_HREmployeeID"))
	private Set<HREmployees> employees;

	public BRBranchs() {
		this.status = AAStatus.Alive.name();
		this.check = true;
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

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean isCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public GELocations getLocation() {
		return location;
	}

	public void setLocation(GELocations location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getContactLevel() {
		return contactLevel;
	}

	public void setContactLevel(String contactLevel) {
		this.contactLevel = contactLevel;
	}

	public Set<HREmployees> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<HREmployees> employees) {
		this.employees = employees;
	}

}
