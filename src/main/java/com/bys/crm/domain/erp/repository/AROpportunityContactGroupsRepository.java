package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.AROpportunityContactGroups;
import com.bys.crm.domain.erp.model.AROpportunitys;

@Repository
public interface AROpportunityContactGroupsRepository extends JpaRepository<AROpportunityContactGroups, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM AROpportunityContactGroups entity")
	Long findMaxId();

	List<AROpportunityContactGroups> findByOpportunityAndOpportunityStatusAndStatus(AROpportunitys opportunity, String opportunityStatus, String groupStatus);

	List<AROpportunityContactGroups> findByContactAndContactStatusAndStatus(ARCustomerContacts contact, String contactStatus, String groupStatus);
}
