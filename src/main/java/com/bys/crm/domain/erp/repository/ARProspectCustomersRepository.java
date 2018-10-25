package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARProspectCustomers;

@Repository
public interface ARProspectCustomersRepository extends JpaRepository<ARProspectCustomers, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM ARProspectCustomers entity")
	Long findMaxId();

	ARProspectCustomers findByIdAndStatus(Long id, String status);

	List<ARProspectCustomers> findByStatus(String status, Sort sort);

	List<ARProspectCustomers> findByStatusAndCreatedDateBetweenOrderByCreatedDate(String status, DateTime startDate,
			DateTime endDate);

	Page<ARProspectCustomers> findByStatus(String status, Pageable requestPage);

	@Query("SELECT entity FROM ARProspectCustomers entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?2"
			+ " OR entity.cellPhone LIKE ?3" + " OR entity.email LIKE ?4" + " OR employee.name LIKE ?5 )"
			+ " AND entity.status = ?6")
	Page<ARProspectCustomers> findByFirstNameLikeOrLastNameLikeOrPhoneLikeOrCellPhoneLikeOrEmailLike(String name,
			String phone, String cellPhone, String email, String assignedTo, String status, Pageable requestPage);

	@Query("SELECT entity FROM ARProspectCustomers entity"
			+ " WHERE ( entity.lastName + ' ' + entity.firstName) LIKE ?1 AND entity.status = ?2")
	List<ARProspectCustomers> findByFirstNameLikeAndStatusOrderByFirstNameAsc(String name, String status);

	@Query("SELECT entity FROM ARProspectCustomers entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?1"
			+ " OR entity.cellPhone LIKE ?1" + " OR entity.email LIKE ?1" + " OR employee.name LIKE ?1 )"
			+ " AND ('%' = ?2 OR entity.rate = ?2)" + " AND (entity.customerResource.id = ?3)"
			+ " AND ('%' = ?4 OR entity.business = ?4)" + " AND (entity.createdDate BETWEEN ?5 AND ?6)"
			+ " AND entity.status = ?7")
	Page<ARProspectCustomers> findByRateLikeAndPotentialSourceLikeAndBusinessLikeAndStatus(String searchKey,
			String potentialRate, Long potentialSourceId, String business, DateTime fromDate, DateTime toDate,
			String status, Pageable requestPage);

	@Query("SELECT entity FROM ARProspectCustomers entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " (( entity.lastName + ' ' + entity.firstName) LIKE ?1" + " OR entity.phone LIKE ?1"
			+ " OR entity.cellPhone LIKE ?1" + " OR entity.email LIKE ?1" + " OR employee.name LIKE ?1 )"
			+ " AND ('%' = ?2 OR entity.rate = ?2)" + " AND ('%' = ?3 OR entity.business = ?3)"
			+ " AND (entity.createdDate BETWEEN ?4 AND ?5)" + " AND entity.status = ?6")
	Page<ARProspectCustomers> findByRateLikeAndPotentialSourceLikeAndBusinessLikeAndStatusNotPotentialSourceId(
			String searchKey, String potentialRate, String business, DateTime fromDate, DateTime toDate, String status,
			Pageable requestPage);

	// Search phone
	@Query("SELECT entity FROM ARProspectCustomers entity" + " WHERE (entity.phone = ?1" + " OR entity.cellPhone = ?1)"
			+ " AND entity.status = ?2")
	List<ARProspectCustomers> findByPhone(String phone, String status);

}
