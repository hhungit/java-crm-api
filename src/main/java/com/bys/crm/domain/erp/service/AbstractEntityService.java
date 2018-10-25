package com.bys.crm.domain.erp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bys.crm.domain.DomainEntity;

public abstract class AbstractEntityService<E extends DomainEntity<Long>, R extends JpaRepository<E, Long>> {
	protected abstract R getRepository();
	
	public E findById(Long id) {
		return getRepository().findOne(id);
	}
	
	public E findActiveById(Long id) {
		E entity = this.findById(id);
		if (entity == null || !entity.isActive()) {
			return null;
		}
		
		return entity;
	}
	
	public E save(E entity) {
		return this.getRepository().save(entity);
	}
	
	public List<E> save(Collection<E> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return new ArrayList<>();
		}
		return this.getRepository().save(entities);
	}
	
}
