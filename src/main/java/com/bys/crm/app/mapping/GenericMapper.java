package com.bys.crm.app.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper {
	@Autowired
	private Mapper mapper;
	
	public <T> T buildObject(Object source, Class<T> clazz) {
		return null == source ? null
				: (T) mapper.map(source, clazz);
	}
	
	public <T> List<T> buildObjects(Collection<?> sources, Class<T> clazz) {
		if (CollectionUtils.isEmpty(sources)) {
			return new ArrayList<T>();
		}
		
		return sources.stream().map(source -> buildObject(source, clazz)).collect(Collectors.toList());
	}

}