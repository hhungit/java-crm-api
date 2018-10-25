package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARCampaigns;

@Repository
public interface ARCampaignsRepository extends JpaRepository<ARCampaigns, Long>, BaseRepository {
	@Query("SELECT max(entity.id) FROM ARCampaigns entity")
	Long findMaxId();

	ARCampaigns findByIdAndStatus(Long id, String status);

	List<ARCampaigns> findByStatus(String status, Sort sort);

	List<ARCampaigns> findByStatusAndCreatedDateBetweenOrderByCreatedDate(String status, DateTime startDate,
			DateTime endDate);

	Page<ARCampaigns> findByStatus(String status, Pageable pageRequest);

	@Query("SELECT entity FROM ARCampaigns entity" + " LEFT JOIN entity.employee employee" + " WHERE "
			+ "( entity.name LIKE ?1" + " OR employee.name LIKE ?2 )" + " AND entity.status = ?3")
	Page<ARCampaigns> findByNameLikeOrEmployeeNameLike(String name, String assignedTo, String status,
			Pageable requestPage);

	List<ARCampaigns> findByNameLikeAndStatusOrderByNameAsc(String name, String status);

	@Query("SELECT entity FROM ARCampaigns entity" + " LEFT JOIN entity.employee employee" + " WHERE "
			+ "( entity.name LIKE ?1" + " OR employee.name LIKE ?1 )" + " AND ('%' = ?2 OR entity.type = ?2)"
			+ " AND ('%' = ?3 OR entity.campaignStatus = ?3)" + " AND (entity.startDate BETWEEN ?4 AND ?5)"
			+ " AND entity.status = ?6")
	Page<ARCampaigns> findByTypeLikeAndCampaignStatusLikeAndStatus(String searchKey, String type, String campaignStatus,
			DateTime fromDate, DateTime toDate, String status, Pageable requestPage);

	List<ARCampaigns> findByStatusAndCampaignNo(String status, String campaignNo);
}
