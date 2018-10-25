package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADUserDevices;


@Repository
public interface ADUserDevicesRepository extends JpaRepository<ADUserDevices, Long>{
	@Query("SELECT max(entity.id) FROM ADUserDevices entity")
	Long findMaxId();
	
	ADUserDevices findByDeviceKey(String deviceKey);
}
