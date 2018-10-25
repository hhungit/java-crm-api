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

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;

@Entity
@Audited
@Table(name = "PMTasks")
public class PMTasks extends DomainEntity<Long> {
	@Id
	@Column(name = "PMTaskID", nullable = false)
	private Long id;

	@Column(name = "PMTaskName")
	private String name;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "PMTaskActualStartDate")
	private DateTime startDate;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "PMTaskActualEndDate")
	private DateTime endDate;

	@Column(name = "PMTaskStatus")
	private String activityStatus;

	@Column(name = "PMTaskLocation", columnDefinition = "nvarchar(256)")
	private String address;

	@Column(name = "ObjectType")
	private String activityObjectType;

	@Column(name = "ObjectTypeName")
	private String activityObjectTypeName;

	@Column(name = "FK_ObjectID")
	private Long activityObjectTypeId;

	@Column(name = "PMTaskInfo")
	private String description;

	@Column(name = "PMTaskType")
	private String taskType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID")
	@NotAudited
	private BRBranchs branch;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
	private List<PMTaskAssigns> taskAssigns;

	@Column(name = "FK_HREmployeeCreatedID")
	private Integer createdUserId;

	@Column(name = "FK_HREmployeeUpdatedID")
	private Integer updatedUserId;

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

	@Column(name = "AAStatus")
	private String status;

	@Column(name = "REV", insertable = false , updatable = false)
	private Long rev;

	@Column(name = "REVTYPE", insertable = false , updatable = false)
	private Long revType;

	@Column(name = "IsShare", columnDefinition = "bit")
	@NotAudited
	private Boolean isShare;

	public PMTasks() {
		this.status = AAStatus.Alive.name();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivityObjectTypeName() {
		return activityObjectTypeName;
	}

	public void setActivityObjectTypeName(String activityObjectTypeName) {
		this.activityObjectTypeName = activityObjectTypeName;
	}

	public Long getActivityObjectTypeId() {
		return activityObjectTypeId;
	}

	public void setActivityObjectTypeId(Long activityObjectTypeId) {
		this.activityObjectTypeId = activityObjectTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
	}

	public List<PMTaskAssigns> getTaskAssigns() {
		return taskAssigns;
	}

	public void setTaskAssigns(List<PMTaskAssigns> taskAssigns) {
		this.taskAssigns = taskAssigns;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Integer getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivityObjectType() {
		return activityObjectType;
	}

	public void setActivityObjectType(String activityObjectType) {
		this.activityObjectType = activityObjectType;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public Long getRevType() {
		return revType;
	}

	public void setRevType(Long revType) {
		this.revType = revType;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

}
