package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.GENumbering;

@Repository
public interface GENumberingRepository  extends JpaRepository<GENumbering, Long>{
	GENumbering findByNameAndBranchId(String name, int branchId);
}
