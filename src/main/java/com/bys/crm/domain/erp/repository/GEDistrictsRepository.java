package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.GEDistricts;

@Repository
public interface GEDistrictsRepository extends JpaRepository< GEDistricts, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM GEDistricts entity")
	Long findMaxId();

	GEDistricts findByIdAndStatus(Long id, String status);

	List<GEDistricts> findByStatusOrderByNameAsc(String status);

}
