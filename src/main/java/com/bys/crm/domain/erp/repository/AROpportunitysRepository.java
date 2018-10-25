package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.AROpportunitys;

@Repository
public interface AROpportunitysRepository extends JpaRepository<AROpportunitys, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM AROpportunitys entity")
	Long findMaxId();

	AROpportunitys findByIdAndStatus(Long id, String status);

	List<AROpportunitys> findByStatus(String status, Sort sort);

	List<AROpportunitys> findByStatusAndCreatedDateBetweenOrderByCreatedDate(String status, DateTime startDate,
			DateTime endDate);

	Page<AROpportunitys> findByStatus(String status, Pageable pageRequest);

	@Query("SELECT distinct(opportunity) FROM AROpportunitys opportunity"
			+ " LEFT JOIN opportunity.opportunityContactGroups opportunityContactGroups"
			+ " LEFT JOIN opportunityContactGroups.contact customerContacts"
			+ " LEFT JOIN opportunity.customer customer" + " LEFT JOIN opportunity.employee employee"
			+ " WHERE (opportunity.name LIKE ?1 OR" + " customer.name LIKE ?2"
			+ " OR ( customerContacts.lastName + ' ' + customerContacts.firstName) LIKE ?3"
			+ " OR employee.name LIKE ?4 )" + " AND opportunity.status = ?5" + " AND opportunity.branch.id IN ?6"
			+ " AND (1 = ?7 OR opportunity.createdUserId = ?8 OR opportunity.isShare = ?9 )")
	List<AROpportunitys> findByNameLikeOrCustomerNameLikeOrCustomerContactNameLike(String name, String customerName,
			String contactName, String assignedTo, String status, Integer[] branchIds, Integer isLeader,
			Integer createdUserId, Boolean isShare);

	List<AROpportunitys> findByNameLikeAndStatusOrderByNameAsc(String name, String status);

	@Query("SELECT distinct(opportunity) FROM AROpportunitys opportunity"
			+ " LEFT JOIN opportunity.opportunityContactGroups opportunityContactGroups"
			+ " LEFT JOIN opportunityContactGroups.contact customerContacts"
			+ " LEFT JOIN opportunity.customer customer" + " LEFT JOIN opportunity.employee employee"
			+ " WHERE (opportunity.name LIKE ?1 OR" + " customer.name LIKE ?1"
			+ " OR ( customerContacts.lastName + ' ' + customerContacts.firstName) LIKE ?1"
			+ " OR employee.name LIKE ?1 )" + " AND ('%' = ?2 OR opportunity.classify = ?2)"
			+ " AND ('%' = ?3 OR opportunity.step = ?3)" + " AND (opportunity.createdDate BETWEEN ?4 AND ?5)"
			+ " AND opportunity.status = ?6" + " AND opportunity.branch.id IN ?7"
			+ " AND (1 = ?8 OR opportunity.createdUserId = ?9 OR opportunity.employee.id = ?9 OR opportunity.employeeGroup.id IN ?10 OR opportunity.isShare = ?11)")
//			+ " ORDER BY ?7")
	List<AROpportunitys> findByClassifyLikeAndStepLikeAndStatus(String searchKey, String classify, String step,
			DateTime fromDate, DateTime toDate, String status, Integer[] branchIds, Integer isLeader,
			Integer createdUserId, Integer[] groupIds, Boolean isShare);

	// Get opportunity list by customer id
	Page<AROpportunitys> findByCustomerIdAndStatus(Long customerId, String status, Pageable requestPage);

}
