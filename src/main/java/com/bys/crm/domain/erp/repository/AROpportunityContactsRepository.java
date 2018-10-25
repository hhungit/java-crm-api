package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.AROpportunityContacts;

@Repository
public interface AROpportunityContactsRepository extends JpaRepository<AROpportunityContacts, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM AROpportunityContacts entity")
	Long findMaxId();

	List<AROpportunityContacts> findByOpportunityIdAndStatusOrderByIdDesc(Long id, String status);
}
