package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADConfigValues;

@Repository
public interface ADConfigValuesRepository extends JpaRepository< ADConfigValues, Long> {
	List<ADConfigValues> findByStatusAndGroup(String status, String group);
	
	List<ADConfigValues> findByStatusAndGroupAndActive(String status, String group, boolean isActive);
}
