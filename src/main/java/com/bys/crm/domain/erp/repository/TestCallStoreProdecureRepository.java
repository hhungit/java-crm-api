package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bys.crm.app.dto.MyObject;

@Repository
public interface TestCallStoreProdecureRepository {

	List<MyObject> getSomeLegacyData(String firstParameter);
}
