package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCustomers;

@Repository
public interface ARCustomersRepository extends JpaRepository<ARCustomers, Long>, BaseRepository {

	List<ARCustomers> findByCustomerType(String customerType);

	@Query("SELECT max(entity.id) FROM ARCustomers entity")
	Long findMaxId();

	List<ARCustomers> findByEmailAndTel1AndStatusOrderByIdDesc(String email, String phone, String status);

	List<ARCustomers> findByEmailAndStatusOrderByIdDesc(String email, String status);
	
	List<ARCustomers> findByStatusAndCreatedDateBetweenOrderByCreatedDate(String status, DateTime startDate, DateTime endDate);

	ARCustomers findByIdAndStatus(Long id, String status);
	
	List<ARCustomers> findByNameAndStatus(String name, String status);

	List<ARCustomers> findByStatus(String status, Sort sort);

	Page<ARCustomers> findByStatus(String status, Pageable requestPage);

	@Query("SELECT entity FROM ARCustomers entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " ( entity.name LIKE ?1" + " OR entity.tel1 LIKE ?2" + " OR entity.email LIKE ?3"
			+ " OR entity.website LIKE ?4" + " OR employee.name LIKE ?5 )" + " AND entity.status = ?6")
	Page<ARCustomers> findByNameLikeOrTel1LikeOrEmailLikeOrWebsiteLike(String name, String phone, String email,
			String website, String assignedTo, String status, Pageable requestPage);

	List<ARCustomers> findByNameLikeAndStatusOrderByNameAsc(String name, String status);

	@Query("SELECT entity FROM ARCustomers entity" + " LEFT JOIN entity.employee employee WHERE"
			+ " ( entity.name LIKE ?1" + " OR entity.tel1 LIKE ?1" + " OR entity.email LIKE ?1"
			+ " OR entity.website LIKE ?1" + " OR employee.name LIKE ?1 )" + " AND ('%' = ?2 OR entity.business = ?2)"
			+ " AND ('%' = ?3 OR entity.group = ?3)" + " AND (CONVERT(INT, 0) IN ?4 OR entity.evaluate IN ?4)"
			+ " AND (entity.createdDate BETWEEN ?5 AND ?6)"
			+ " AND entity.status = ?7")
	Page<ARCustomers> findByBusinessLikeAndGroupLikeAndEvaluateLikeAndStatus(String searchKey, String business, String group, Long[] evaluate,
			DateTime fromDate, DateTime toDate, String status, Pageable requestPage);

	// Get customer list by array id
	@Query("SELECT entity FROM ARCustomers entity"
			+ " WHERE entity.id IN ?1")
	List<ARCustomers> findByIds(Long[] id);

	// Search phone
	@Query("SELECT entity FROM ARCustomers entity"
			+ " WHERE (entity.phone = ?1" + " OR entity.companyPhone = ?1 " + " OR entity.tel1 = ?1 "
			+ " OR entity.tel2 = ?1)" + " AND entity.status = ?2")
	List<ARCustomers> findByPhone(String phone, String status);
}
