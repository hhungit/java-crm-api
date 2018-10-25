package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADFengShuisColors;

@Repository
public interface ADFengShuisColorsRepository extends JpaRepository<ADFengShuisColors, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ADFengShuisColors entity")
	Long findMaxId();

	ADFengShuisColors findByFengShuisColorLifeLike(String colorLife);
}
