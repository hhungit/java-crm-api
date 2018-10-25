package com.bys.crm.app.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.ConfigValueDto;
import com.bys.crm.app.dto.CustomerResourcesDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.constant.ADConfigKeyGroup;
import com.bys.crm.domain.erp.constant.PotentialSource;
import com.bys.crm.domain.erp.model.ADConfigValues;
import com.bys.crm.domain.erp.model.ARCustomerResources;
import com.bys.crm.domain.erp.repository.ADConfigValuesRepository;
import com.bys.crm.domain.erp.repository.ARCustomerResourceRepository;

@Service
public class ListFacade {
	@Autowired
	private GenericMapper mapper;

	@Autowired
	private ADConfigValuesRepository configValuesRepository;
	
	@Autowired
	private ARCustomerResourceRepository customerResourceRepository;

	public List<ConfigValueDto> getCommonList(String listType) {
		if (ADConfigKeyGroup.fromValue(listType) == null) {
			throw new InvalidException("Valid list type values is " + PotentialSource.supportValues(),
					ErrorCodeEnum.INVALID_CONFIG_TYPE);
		}
		List<ADConfigValues> entities = configValuesRepository.findByStatusAndGroupAndActive(AAStatus.Alive.name(),
				ADConfigKeyGroup.fromValue(listType).name(), true);

		return entities.stream().map(entity -> mapper.buildObject(entity, ConfigValueDto.class))
				.collect(Collectors.toList());
	}

	public List<CustomerResourcesDto> getCustomerResources(){		
		List<ARCustomerResources> entities = customerResourceRepository.findByStatus(AAStatus.Alive.name());
		return entities.stream().map(entity -> mapper.buildObject(entity, CustomerResourcesDto.class))
				.collect(Collectors.toList());
	}
}
