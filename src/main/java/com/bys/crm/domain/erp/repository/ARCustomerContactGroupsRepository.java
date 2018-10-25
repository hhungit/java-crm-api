package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCustomerContactGroups;
import com.bys.crm.domain.erp.model.ARCustomerContacts;
import com.bys.crm.domain.erp.model.ARCustomers;

@Repository
public interface ARCustomerContactGroupsRepository extends JpaRepository<ARCustomerContactGroups, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM ARCustomerContactGroups entity")
	Long findMaxId();

	List<ARCustomerContactGroups> findByCustomerAndCustomerStatusAndStatus(ARCustomers customer, String customerStatus, String groupStatus);

	List<ARCustomerContactGroups> findByContactAndContactStatusAndStatus(ARCustomerContacts contact, String contactStatus, String groupStatus);
}
