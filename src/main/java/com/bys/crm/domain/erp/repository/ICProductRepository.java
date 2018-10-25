package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ICProducts;

@Repository
public interface ICProductRepository extends JpaRepository<ICProducts, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM ICProducts entity")
	Long findMaxId();
	
	List<ICProducts> findByStatus(String status);
}
