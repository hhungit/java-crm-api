package com.bys.crm.domain.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADFengShuisDirections;

@Repository
public interface ADFengShuisDirectionsRepository extends JpaRepository<ADFengShuisDirections, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ADFengShuisDirections entity")
	Long findMaxId();

	ADFengShuisDirections findByFengShuisDirectionMainLike(String directionMain);
}
