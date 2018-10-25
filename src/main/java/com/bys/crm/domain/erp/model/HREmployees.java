package com.bys.crm.domain.erp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.bys.crm.domain.DomainEntity;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.Gender;
import com.bys.crm.domain.erp.model.converter.GenderConverter;

@Entity
@Table(name = "HREmployees")
public class HREmployees extends DomainEntity<Integer> {
	@Id
	@Column(name = "HREmployeeID", nullable = false)
	private Integer id;

	@Column(name = "HREmployeeName", nullable = false, columnDefinition = "nvarchar(150)")
	private String name;

	@Column(name = "HREmployeeNo", nullable = false, columnDefinition = "nvarchar(50)")
	private String employeeNumber;

	@Column(name = "FK_HRDepartmentID", nullable = false)
	private Long departmentId;

	@Column(name = "FK_HRDepartmentRoomID", nullable = false)
	private Long departmentRoomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_BRBranchID", nullable = false)
	private BRBranchs branch;

	@Column(name = "AAStatus", columnDefinition = "varchar(10)")
	private String status;

	@Column(name = "HREmployeeFirstName", columnDefinition = "nvarchar(50)")
	private String firstName;

	@Column(name = "HREmployeeLastName", columnDefinition = "nvarchar(50)")
	private String lastName;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "HREmployeeDob")
	private DateTime dob;

	@Column(name = "HREmployeeBirthPlace", columnDefinition = "nvarchar(100)")
	private String birthPlace;

	@Column(name = "HREmployeeIDNumber", columnDefinition = "nvarchar(50)")
	private String employeeIdNumber;

	@Column(name = "HREmployeeGenderCombo")
	@Convert(converter = GenderConverter.class)
	private Gender gender;

	@Column(name = "HREmployeeTel1", columnDefinition = "nvarchar(50)")
	private String phone;

	@Column(name = "HREmployeeEmail1", columnDefinition = "nvarchar(50)")
	private String email;

	@Column(name = "HREmployeePassword1", columnDefinition = "nvarchar(50)")
	private String password;

	@Column(name = "HREmployeeTaxNumber", columnDefinition = "varchar(50)")
	private String taxNumber;

	@Column(name = "HREmployeeContactAddressLine1", columnDefinition = "nvarchar(200)")
	private String address;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ADPortalUserID", columnDefinition = "int")
	private ADUsers user;

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

	@Column(name = "HREmployeePicture", columnDefinition = "varbinary(MAX)")
	private byte[] avatar;

	@ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
	private Set<HRGroups> groups;

	@ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
	private Set<BRBranchs> branchs;

	public HREmployees() {
		this.status = AAStatus.Alive.name();
		this.departmentId = Long.valueOf(1);
		this.departmentRoomId = Long.valueOf(1);
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

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDepartmentRoomId() {
		return departmentRoomId;
	}

	public void setDepartmentRoomId(Long departmentRoomId) {
		this.departmentRoomId = departmentRoomId;
	}

	public BRBranchs getBranch() {
		return branch;
	}

	public void setBranch(BRBranchs branch) {
		this.branch = branch;
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

	public DateTime getDob() {
		return dob;
	}

	public void setDob(DateTime dob) {
		this.dob = dob;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getEmployeeIdNumber() {
		return employeeIdNumber;
	}

	public void setEmployeeIdNumber(String employeeIdNumber) {
		this.employeeIdNumber = employeeIdNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ADUsers getUser() {
		return user;
	}

	public void setUser(ADUsers user) {
		this.user = user;
	}

	public Set<HRGroups> getGroups() {
		if (groups == null) {
			groups = new HashSet<>();
		}
		return groups;
	}

	public void setGroups(Set<HRGroups> groups) {
		this.groups = groups;
	}
//
//	public Set<ADPrivilegeGroups> getPrivilegeGroups() {
//		if (privilegeGroups == null) {
//			privilegeGroups = new HashSet<>();
//		}
//		return privilegeGroups;
//	}
//
//	public void setPrivilegeGroups(Set<ADPrivilegeGroups> privilegeGroups) {
//		this.privilegeGroups = privilegeGroups;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Set<BRBranchs> getBranchs() {
		return branchs;
	}

	public void setBranchs(Set<BRBranchs> branchs) {
		this.branchs = branchs;
	}

}
