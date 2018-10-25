package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.PMTasks;

@Repository
public interface PMTasksRepository extends JpaRepository<PMTasks, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM PMTasks entity")
	Long findMaxId();

	PMTasks findByIdAndStatus(Long id, String status);

	List<PMTasks> findByStatus(String status, Sort sort);

	Page<PMTasks> findByStatus(String status, Pageable pageRequest);

	@Query("SELECT entity FROM PMTasks entity" + " LEFT JOIN entity.taskAssigns taskAssigns"
			+ " LEFT JOIN taskAssigns.employee employee" + " WHERE ( entity.name LIKE ?1" + " OR entity.address LIKE ?2"
			+ " OR employee.name LIKE ?3 )" + " AND entity.status = ?4" + " AND entity.taskType = ?5"
			+ " AND entity.branch.id IN ?6" + " AND (1 = ?7 OR entity.createdUserId = ?8 OR entity.isShare = ?9)")
	List<PMTasks> findByNameLikeOrAddressLikeOrEmployeeNameLike(String name, String address, String assignedTo,
			String status, String taskType, Integer[] branchIds, Integer isLeader, Integer createdUserId,
			Boolean isShare);

	List<PMTasks> findByTaskTypeAndTaskAssignsEmployeeIdAndStartDateGreaterThanEqualAndStatusOrderByStartDate(
			String activityType, Integer employeeId, DateTime startDate, String status);

	List<PMTasks> findByActivityObjectTypeAndActivityObjectTypeIdAndStatusAndTaskType(String activityObjectType,
			Long activityObjectTypeId, String status, String taskType);

	@Query("SELECT entity FROM PMTasks entity" + " LEFT JOIN entity.taskAssigns taskAssigns"
			+ " LEFT JOIN taskAssigns.employee employee"
			+ " WHERE ( taskAssigns.employee.id = ?1 OR taskAssigns.employeeGroup.id IN ?2 )"
			+ " AND entity.endDate >= ?3  AND entity.status = ?4")
	Page<PMTasks> findByActivityTypeAndEmployeeIdAndStartDateGreaterThanEqualAndStatus(Integer employeeId,
			Integer[] groupId, DateTime startDate, String status, Pageable requestPage);

	Page<PMTasks> findByStatusAndStartDateGreaterThanEqualAndTaskAssignsEmployeeId(String status, DateTime startDate,
			Integer employeeId, Pageable requestPage);

	@Query("SELECT entity FROM PMTasks entity" + " LEFT JOIN entity.taskAssigns taskAssigns"
			+ " LEFT JOIN taskAssigns.employee employee " + " WHERE ( entity.name LIKE ?1" + " OR entity.address LIKE ?1"
			+ " OR employee.name LIKE ?1 )"
			+ " AND ('%' = ?2 OR entity.activityStatus = ?2)" + " AND (entity.startDate BETWEEN ?3 AND ?4)"
			+ " AND entity.status = ?5" + " AND entity.taskType = ?6" + " AND entity.branch.id IN ?7"
			+ " AND (1 = ?8 OR entity.createdUserId = ?9 OR entity.isShare = ?10 )"
			+ " AND (0 = ?11 OR entity.createdUserId = ?11)"
			+ " AND (0 = ?12 OR taskAssigns.employee.id = ?12)"
			+ " AND (0 = ?13 OR taskAssigns.employeeGroup.id = ?13)")
	List<PMTasks> findByActivityTypeLikeAndActivityStatusLikeAndStatus2(String searchKey, String activityStatus,
			DateTime fromDate, DateTime toDate, String status, String taskType, Integer[] branchIds, Integer isLeader,
			Integer createdUserId, Boolean isShare, Integer createdBy, Integer assignmentEmployeeId,Integer assignmentEmployeeGroupId );
}
