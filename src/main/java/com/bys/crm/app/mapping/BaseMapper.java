package com.bys.crm.app.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bys.crm.domain.erp.service.KeyGenerationService;
import com.bys.crm.util.RelectionUtil;

public abstract class BaseMapper<DTO, E> {
	@Autowired
	private Mapper mapper;
	
	@Autowired
	protected GenericMapper genericMapper;
	@Autowired
	protected KeyGenerationService keyGenerationService;

	@SuppressWarnings("unchecked")
	public DTO buildDto(E entity) {
		return null == entity ? null
				: (DTO) mapper.map(entity, RelectionUtil.getClassParameterType(this.getClass(), 0));
	}
	
	@SuppressWarnings("unchecked")
	public E buildEntity(DTO dto) {
		return null == dto ? null
				: (E) mapper.map(dto, RelectionUtil.getClassParameterType(this.getClass(), 1));
	}
	
	public List<DTO> buildDtos(Collection<E> entities) {
		return CollectionUtils.isEmpty(entities)? new ArrayList<>():
				entities.stream().map(entity -> buildDto(entity)).collect(Collectors.toList());
	}
	
	
	public List<E> buildEntities(Collection<DTO> dtos) {
		return CollectionUtils.isEmpty(dtos)? new ArrayList<>():
				dtos.stream().map(dto -> buildEntity(dto)).collect(Collectors.toList());
	}

}
