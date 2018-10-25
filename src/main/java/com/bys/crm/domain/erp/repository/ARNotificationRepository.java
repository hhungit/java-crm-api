package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARNotifications;

@Repository
public interface ARNotificationRepository extends JpaRepository<ARNotifications, Long>, BaseRepository {

	@Override
	@Query("SELECT max(entity.id) FROM ARNotifications entity")
	Long findMaxId();

	@Query("SELECT COUNT(entity) FROM ARNotifications entity"
			+ " WHERE ( entity.employee.id = ?1 OR entity.employeeGroup.id IN ?2 )"
			+ " AND entity.status = ?3 AND entity.read = ?4")
	Long countByEmployeeIdOrGroupIdAndStatusAndRead(Integer employeeId, Integer[] groupId, String status, short read);

	@Query("SELECT entity FROM ARNotifications entity"
			+ " WHERE ( entity.employee.id = ?1 OR entity.employeeGroup.id IN ?2 )" + " AND entity.status = ?3")
	Page<ARNotifications> findByEmployeeIdOrGroupIdAndStatusOrderByIdDesc(Integer employeeId, Integer[] groupId,
			String status, Pageable pageRequest);

	@Query("SELECT entity FROM ARNotifications entity" + " WHERE (entity.startDate >= ?1 AND entity.startDate < ?2)"
			+ " AND ( entity.employee.id = ?3 OR entity.employeeGroup.id IN ?4 )" + " AND entity.status = ?5")
	Page<ARNotifications> findByStartDateAndStatus(DateTime fromDate, DateTime toDate, Integer employeeId,
			Integer[] groupId, String status, Pageable pageRequest);

	ARNotifications findByIdAndStatus(Long id, String status);
	
	// Get notification by objectId
	List<ARNotifications> findByObjectTypeAndObjectIdAndStatus(String objectType, Long objectId, String status);
}
