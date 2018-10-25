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
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Table(name = "ARComments")
public class ARComments extends DomainEntity<Long> {
	@Id
	@Column(name = "ARCommentID", columnDefinition = "int", nullable = false)
	private Long id;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "AACreatedUser", columnDefinition = "nvarchar(50)")
	private String createdUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AACreatedDate")
	private DateTime createdDate;

	@Column(name = "AAUpdatedUser", columnDefinition = "nvarchar(50)")
	private String updatedUser;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "AAUpdatedDate")
	private DateTime updatedDate;

	@Column(name = "ARCommentType", nullable = false, columnDefinition = "nvarchar(50)")
	private String type;

	@Column(name = "ARCommentObjectID")
	private Long objectId;

	@Column(name = "ARCommentDescription", columnDefinition = "nvarchar(512)")
	private String description;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "ARCommentDate")
	private DateTime date;

	@Column(name = "ARCommentParentID")
	private Long parentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_HREmployeeID")
	private HREmployees employee;

	public ARComments() {
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

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public HREmployees getEmployee() {
		return employee;
	}

	public void setEmployee(HREmployees employee) {
		this.employee = employee;
	}

}
