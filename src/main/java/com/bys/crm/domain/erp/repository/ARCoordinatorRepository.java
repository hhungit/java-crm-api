package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCoordinators;

@Repository
public interface ARCoordinatorRepository extends JpaRepository< ARCoordinators, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM ARCoordinators entity")
	Long findMaxId();
	
	List<ARCoordinators> findByStatusAndCoordinatorNo(String status, String coordinatorNo);
}
