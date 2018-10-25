package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.BRBranchs;

@Repository
public interface BRBranchsRepository extends JpaRepository< BRBranchs, Long>, BaseRepository{
	@Query("SELECT max(entity.id) FROM BRBranchs entity")
	Long findMaxId();

	List<BRBranchs> findByStatus(String status);

	BRBranchs findByStatusAndId(String status, Integer id);

	List<BRBranchs> findByStatusAndIdIn(String status, List<Long> ids);
}
