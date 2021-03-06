package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.CRMCallCenterManagerCdrs;

@Repository
public interface CRMCallCenterManagerCdrsRepository extends JpaRepository<CRMCallCenterManagerCdrs, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM CRMCallCenterManagerCdrs entity")
	Long findMaxId();
}
