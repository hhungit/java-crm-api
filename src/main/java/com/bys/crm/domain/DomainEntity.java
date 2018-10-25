package com.bys.crm.domain;

import com.bys.crm.domain.erp.constant.AAStatus;

public abstract class DomainEntity<ID> {
	
	public abstract ID getId();
	public abstract void setId(ID id);
	
	public abstract String getStatus();
	
	public boolean isActive() {
		return AAStatus.Alive.name().equals(getStatus());
	}

}
