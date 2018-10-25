package com.bys.crm.domain.erp.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ACDocumentEntrys;

@Repository
public interface ACDocumentEntrysRepository extends JpaRepository<ACDocumentEntrys, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ACDocumentEntrys entity")
	Long findMaxId();

//	@Query("SELECT doc.documentDate as documentDate, doc.documentNo as documentNo,doc.documentDesc as documentDesc,"
//			+ " doc.documentTotalAmount as documentTotalAmount, doc.documentExchangeAmount as documentExchangeAmount,"
//			+ " doc.documentTypeId as documentTypeId,"
//			+ " entry.entryTypeId as entryTypeId, entry.debitAccountId as debitAccountId,"
//			+ " entry.creditAccountId as creditAccountId, entry.documentEntryDesc as documentEntryDesc"
	@Query("SELECT entry" + " FROM ACDocumentEntrys entry"
//			+ " INNER JOIN entry.document doc"
			+ " WHERE entry.objectId = ?1 AND entry.entryTypeId IN (8, 42)"
			+ " AND (entry.debitAccountId = 20 OR entry.creditAccountId = 20)"
			+ " AND entry.document.documentDate BETWEEN ?2 AND ?3"
			+ " AND entry.status LIKE ?4 AND entry.document.status LIKE ?5"
			+ " ORDER BY entry.document.documentDate")
//	Page<ACDocumentEntrys> getDebtDetails(Long customerId, String entryStatus,
//			String documentStatus, Pageable requestPage);
	Page<ACDocumentEntrys> getDebtDetails(Long customerId, DateTime fromDate, DateTime toDate, String entryStatus,
			String documentStatus, Pageable requestPage);
}
