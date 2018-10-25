package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADFengShuisGenarals;

@Repository
public interface ADFengShuisGenaralsRepository extends JpaRepository<ADFengShuisGenarals, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ADFengShuisGenarals entity")
	Long findMaxId();

	ADFengShuisGenarals findByStatusAndFengShuisGenaralBirthdateLike(String status, String birthday);

	List<ADFengShuisGenarals> findByStatus(String status);
}
