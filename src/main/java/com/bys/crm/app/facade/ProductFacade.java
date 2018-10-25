package com.bys.crm.app.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.ProductDto;
import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.app.mapping.GenericMapper;
import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.HREmployees;
import com.bys.crm.domain.erp.model.ICProducts;
import com.bys.crm.domain.erp.repository.HREmployeesRepository;
import com.bys.crm.domain.erp.repository.ICProductRepository;

@Service
public class ProductFacade {

	@Autowired
	private ICProductRepository repository;

	@Autowired
	private GenericMapper genericMapper;

	@Autowired
	private HREmployeesRepository employeesRepository;

	public List<ProductDto> getProducts(Integer employeeId) {
		// Validate employee id
		HREmployees employee = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
		if (employee == null) {
			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.INVALID_REQUEST);
		}

		List<ICProducts> entities = repository.findByStatus(AAStatus.Alive.name());

		return genericMapper.buildObjects(entities, ProductDto.class);
	}

}
