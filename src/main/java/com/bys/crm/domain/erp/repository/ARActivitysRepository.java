package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARActivitys;

@Repository
public interface ARActivitysRepository extends JpaRepository<ARActivitys, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM ARActivitys entity")
	Long findMaxId();

	ARActivitys findByIdAndStatus(Long id, String status);

	List<ARActivitys> findByStatus(String status, Sort sort);

	Page<ARActivitys> findByStatus(String status, Pageable pageRequest);

	@Query("SELECT entity FROM ARActivitys entity" + " LEFT JOIN entity.employee employee" + " WHERE "
			+ "( entity.name LIKE ?1" + " OR entity.address LIKE ?2" + " OR employee.name LIKE ?3 )"
			+ " AND entity.status = ?4"
			+ " AND entity.branch.id IN ?5"
			+ " AND (1 = ?6 OR entity.createdUserId = ?7 OR entity.isShare = ?8)")
	List<ARActivitys> findByNameLikeOrAddressLikeOrEmployeeNameLike(String name, String address, String assignedTo,
			String status, Integer[] branchIds, Integer isLeader, Integer createdUserId, Boolean isShare);

	List<ARActivitys> findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatusOrderByStartDate(
			String activityType, Integer employeeId, DateTime startDate, String status);

	List<ARActivitys> findByActivityObjectTypeAndActivityObjectTypeIdAndStatus(String activityObjectType,
			Long activityObjectTypeId, String status);

	@Query("SELECT entity FROM ARActivitys entity"
			+ " WHERE entity.activityType = ?1"
			+ " AND ( entity.employee.id = ?2 OR entity.employeeGroup.id IN ?3 )" + " AND entity.endDate >= ?4"
			+ " AND entity.status = ?5")
	Page<ARActivitys> findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatus(String activityType,
			Integer employeeId, Integer[] groupId, DateTime startDate, String status, Pageable requestPage);

	@Query("SELECT entity FROM ARActivitys entity" + " LEFT JOIN entity.employee employee" + " WHERE "
			+ "( entity.name LIKE ?1" + " OR entity.address LIKE ?1" + " OR employee.name LIKE ?1 )"
			+ " AND ('%' = ?2 OR entity.activityStatus = ?2)"
			+ " AND (entity.startDate BETWEEN ?3 AND ?4)"
			+ " AND entity.status = ?5"
			+ " AND entity.branch.id IN ?6"
			+ " AND (1 = ?7 OR entity.createdUserId = ?8 OR entity.isShare = ?9)"
			+ " AND (0 = ?10 OR entity.createdUserId = ?10)"
			+ " AND (0 = ?11 OR entity.employee.id = ?11)"
			+ " AND (0 = ?12 OR entity.employeeGroup.id = ?12)")
	List<ARActivitys> findByActivityTypeLikeAndActivityStatusLikeAndStatus(String searchKey,
			String activityStatus, DateTime fromDate, DateTime toDate, String status,
			Integer[] branchIds, Integer isLeader, Integer createdUserId, Boolean isShare, 
			Integer createdBy, Integer assignmentEmployeeId, Integer assignmentEmployeeGroupId);
}
