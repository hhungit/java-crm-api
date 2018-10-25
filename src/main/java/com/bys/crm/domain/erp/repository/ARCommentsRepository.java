package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARComments;

@Repository
public interface ARCommentsRepository extends JpaRepository< ARComments, Long>, BaseRepository{
	@Query("SELECT max(entity.id) FROM ARComments entity")
	Long findMaxId();

	List<ARComments> findByStatusAndTypeAndObjectIdAndParentIdOrderByIdDesc(String status, String type, Long objectId, Long parentId);
}
