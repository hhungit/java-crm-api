package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.PMTaskAssigns;

@Repository
public interface PMTaskAssignsRepository extends JpaRepository<PMTaskAssigns, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM PMTaskAssigns entity")
	Long findMaxId();
}
