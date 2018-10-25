package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.CRMCallCenterManagers;

@Repository
public interface CRMCallCenterManagersRepository extends JpaRepository<CRMCallCenterManagers, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM CRMCallCenterManagers entity")
	Long findMaxId();

	List<CRMCallCenterManagers> findByStatusAndCallUuid(String status, String uuid);

	List<CRMCallCenterManagers> findByStatusAndCallStatusIn(String status, List<String> callStatus);

	List<CRMCallCenterManagers> findByStatusAndCallUuidNotIn(String status, List<String> uuid);

	Page<CRMCallCenterManagers> findByStatusAndCallStatusIn(String status, List<String> callStatus,
			Pageable requestPage);
	
	Page<CRMCallCenterManagers> findByStatusAndDirection(String status, String direction,
			Pageable requestPage);

	@Query("select entity from CRMCallCenterManagers entity where entity.callUuid in (select entity.callUuid from CRMCallCenterManagers entity where entity.status = 'Delete' and entity.callStatus = 'HangUp') and entity.status = 'Delete' and entity.callStatus = 'Start' order by entity.createdDate desc")
	Page<CRMCallCenterManagers> findHistoryCalled(Pageable requestPage);
}
