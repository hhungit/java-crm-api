package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.GEStateProvinces;

@Repository
public interface GEStateProvincesRepository extends JpaRepository< GEStateProvinces, Long>, BaseRepository{

	@Query("SELECT max(entity.id) FROM GEStateProvinces entity")
	Long findMaxId();

	GEStateProvinces findByIdAndStatus(Long id, String status);

	List<GEStateProvinces> findByStatusOrderByNameAsc(String status);

}
