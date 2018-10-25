package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.HREmployeeGroups;

@Repository
public interface HREmployeeGroupsRepository extends JpaRepository< HREmployeeGroups, Long>, BaseRepository{
	@Query("SELECT max(entity.id) FROM HREmployeeGroups entity")
	Long findMaxId();

	@Query("SELECT DISTINCT entity FROM HREmployeeGroups entity "
			+ "WHERE entity.status = ?1 AND entity.employeeId = ?2")
	List<HREmployeeGroups> findByStatusAndEmployeeId(String status, Integer employeeId);
	
	@Query("SELECT DISTINCT entity.branchId FROM HREmployeeGroups entity "
			+ "WHERE entity.status = ?1 AND entity.employeeId = ?2")
	List<Long> findBranchIDByStatusAndEmployeeId(String status, Integer employeeId);
}
