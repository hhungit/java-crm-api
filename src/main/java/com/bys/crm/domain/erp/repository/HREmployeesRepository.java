package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.HREmployees;

@Repository
public interface HREmployeesRepository extends JpaRepository< HREmployees, Long>, BaseRepository{
	@Query("SELECT max(entity.id) FROM HREmployees entity")
	Long findMaxId();

	HREmployees findByStatusAndId(String status, Integer id);

	HREmployees findById(Integer id);

	List<HREmployees> findByStatusAndEmployeeNumber(String status, String employeeNumber);

	List<HREmployees> findByStatusOrderByNameAsc(String status);
	
	 @Query("SELECT entity FROM HREmployees entity WHERE  entity.name  <> '' AND entity.status = ?1")
	 List<HREmployees> findByStatusAndNameNotEmptyOrderByNameAsc(String status);

	List<HREmployees> findByStatusAndBranchIdOrderByNameAsc(String status, Long branchId);
	
	Page<HREmployees> findByStatusOrderByNameAsc(String status, Pageable pageRequest);
}
