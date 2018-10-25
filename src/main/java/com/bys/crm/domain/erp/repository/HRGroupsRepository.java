package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.HRGroups;

@Repository
public interface HRGroupsRepository extends JpaRepository< HRGroups, Long>, BaseRepository{
	@Query("SELECT max(entity.id) FROM HRGroups entity")
	Long findMaxId();

	HRGroups findByIdAndStatus(Integer id, String status);

	List<HRGroups> findByStatus(String status);
}
