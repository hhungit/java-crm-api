package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.GECountrys;

@Repository
public interface GECountrysRepository extends JpaRepository< GECountrys, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM GECountrys entity")
	Long findMaxId();

	GECountrys findByIdAndStatus(Long id, String status);

	List<GECountrys> findByStatusOrderByNameAsc(String status);

}
