package com.bys.crm.domain.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bys.crm.domain.DomainEntity;

@Entity
@Table(name = "ADConfigValues")
public class ADConfigValues extends DomainEntity<Long> {
	@Id
	@Column(name = "ADConfigValueID")
	private Long id;
	
	@Column(name = "AAStatus")
	private String status;
	
	@Column(name = "ADConfigKey")
	private String key;
	
	@Column(name = "ADConfigKeyValue")
	private String value;
	
	@Column(name = "ADConfigText")
	private String text;
	
	@Column(name = "ADConfigKeyGroup")
	private String group;
	
	@Column(name = "ADConfigKeyDesc")
	private String desc;
	
	@Column(name = "IsActive")
	private boolean active;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}	
}
