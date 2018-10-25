package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCustomerResources;

@Repository
public interface ARCustomerResourceRepository extends JpaRepository<ARCustomerResources, Long>, BaseRepository {

	List<ARCustomerResources> findByStatus(String status);

	@Query("SELECT max(entity.id) FROM ARCustomerResources entity")
	Long findMaxId();
}
