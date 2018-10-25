package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCustomerContacts;

@Repository
public interface ARCustomerContactRepository extends JpaRepository<ARCustomerContacts, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM ARCustomerContacts entity")
	Long findMaxId();

	ARCustomerContacts findByIdAndStatus(Long id, String status);

	List<ARCustomerContacts> findByStatus(String status, Sort sort);

	List<ARCustomerContacts> findByStatusAndCreatedDateBetweenOrderByCreatedDate(String status, DateTime startDate,
			DateTime endDate);

	Page<ARCustomerContacts> findByStatus(String status, Pageable pageRequest);

	@Query("SELECT entity FROM ARCustomerContacts entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?2"
			+ " OR entity.cellularPhone LIKE ?3" + " OR entity.email LIKE ?4" + " OR employee.name LIKE ?5 )"
			+ " AND entity.status = ?6")
	Page<ARCustomerContacts> findByFirstNameLikeOrLastNameLikeOrPhoneLikeOrCellularPhoneLikeOrEmailLike(String name,
			String phone, String cellularPhone, String email, String assignedTo, String status, Pageable requestPage);

	@Query("SELECT entity FROM ARCustomerContacts entity"
			+ " WHERE ( entity.lastName + ' ' + entity.firstName) LIKE ?1 AND entity.status = ?2")
	List<ARCustomerContacts> findByFirstNameLikeAndStatusOrderByFirstNameAsc(String name, String status);

	@Query("SELECT entity FROM ARCustomerContacts entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?1"
			+ " OR entity.cellularPhone LIKE ?1" + " OR entity.email LIKE ?1" + " OR employee.name LIKE ?1 )"
			+ " AND (entity.customerResource.id = ?2)" + " AND ('%' = ?3 OR entity.jobTitle LIKE ?3)"
			+ " AND (entity.createdDate BETWEEN ?4 AND ?5)" + " AND entity.status = ?6")
	Page<ARCustomerContacts> findByPotentialSourceLikeAndJobTitleLikeAndStatus(String searchKey, Long potentialSourceId,
			String jobTitle, DateTime fromDate, DateTime toDate, String status, Pageable requestPage);

	@Query("SELECT entity FROM ARCustomerContacts entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?1"
			+ " OR entity.cellularPhone LIKE ?1" + " OR entity.email LIKE ?1" + " OR employee.name LIKE ?1 )"
			+ " AND ('%' = ?2 OR entity.jobTitle LIKE ?2)" + " AND (entity.createdDate BETWEEN ?3 AND ?4)"
			+ " AND entity.status = ?5")
	Page<ARCustomerContacts> findByPotentialSourceLikeAndJobTitleLikeAndStatusNotPotentialSourceId(String searchKey,
			String jobTitle, DateTime fromDate, DateTime toDate, String status, Pageable requestPage);

	@Query("SELECT entity FROM ARCustomerContacts entity WHERE" + " (( entity.lastName + ' ' + entity.firstName) = ?1)"
			+ " AND entity.status = ?2")
	List<ARCustomerContacts> findByLastNameAndFirstNameAndStatus(String contactName, String status);

	// Get Contacts by CustomerId
//	List<ARCustomerContacts> findByCustomerIdAndStatus(Long customerId, String status);

	// Get contact list by array id
	@Query("SELECT entity FROM ARCustomerContacts entity" + " WHERE entity.id IN ?1")
	List<ARCustomerContacts> findByIds(Long[] id);

	// Search phone
	@Query("SELECT entity FROM ARCustomerContacts entity" + " WHERE (entity.phone = ?1"
			+ " OR entity.cellularPhone = ?1" + " OR entity.homePhone = ?1" + " OR entity.secondaryPhone = ?1"
			+ " OR entity.assistantPhone = ?1)" + " AND entity.status = ?2")
	List<ARCustomerContacts> findByPhone(String phone, String status);
}
