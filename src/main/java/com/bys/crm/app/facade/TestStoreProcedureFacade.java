package com.bys.crm.app.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.MyObject;
import com.bys.crm.domain.erp.service.TestCallStoreProdecureService;

@Service
public class TestStoreProcedureFacade {

	@Autowired
	private TestCallStoreProdecureService testService;
	
	public List<MyObject> getListTestStore(){
		return testService.getSomeLegacyData("131");
	}
	
	public Long testCountCustomer(){
		return testService.testCountCustomer();
	}
	
	public List<Object[]> testCountCustomerList(){
		return testService.testCountCustomerList();
	}
	
	public BigDecimal getInitBalance(){
		return testService.getInitBalance("131", 338L, "Customer");
	}
}
