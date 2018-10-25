package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.GELocations;

@Repository
public interface GELocationsRepository extends JpaRepository<GELocations, Long> {
	List<GELocations> findByType(String type);
	List<GELocations> findByTypeAndParent(String type, GELocations parent);
}
