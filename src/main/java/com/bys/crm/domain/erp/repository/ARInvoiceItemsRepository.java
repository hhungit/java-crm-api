package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ARInvoiceItems;

@Repository
public interface ARInvoiceItemsRepository extends JpaRepository<ARInvoiceItems, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ARInvoiceItems entity")
	Long findMaxId();

	List<ARInvoiceItems> findByInvoiceCustomerIdAndInvoiceInvoiceStatusAndStatusOrderByInvoiceInvoiceDateDesc(
			Long customerId, String invoiceStatus, String status);

	@Query("SELECT entity FROM ARInvoiceItems entity" + " INNER JOIN entity.invoice invoice"
			+ " INNER JOIN entity.product product" + " WHERE invoice.customerId = ?1"
			+ " AND ('%' = ?2 OR product.productNo = ?2)" + " AND ('%' = ?3 OR invoice.invoiceStatus = ?3)"
			+ " AND invoice.status LIKE ?4" + " AND entity.status LIKE ?5" 
			+ " AND ('%' = ?6 OR invoice.invoiceNo LIKE ?6)"
			+ " ORDER BY invoice.invoiceDate DESC")
	Page<ARInvoiceItems> findByInvoiceCustomerIdAndInvoiceStatusAndStatusOrderByInvoiceInvoiceDateDesc(Long customerId,
			String productNo, String invoiceInvoiceStatus, String invoiceStatus, String itemStatus, String invoiceNo,
			Pageable requestPage);

	@Query("SELECT entity.productName as productName, sum(entity.productQty) as productQty FROM ARInvoiceItems entity"
			+ " INNER JOIN entity.invoice invoice WHERE" + " invoice.customerId = ?1"
			+ " AND invoice.invoiceDate BETWEEN ?2 AND ?3" + " AND invoice.status LIKE ?4"
			+ " AND entity.status LIKE ?5" + " GROUP BY entity.productName")
	List<Object[]> getInvoiceProductQtyOfMonth(Long customerId, DateTime fromDate, DateTime toDate,
			String invoiceStatus, String status);
}
