package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ICProductCustomerPrices;

@Repository
public interface ICProductCustomerPricesRepository
		extends JpaRepository<ICProductCustomerPrices, Long>, BaseRepository {

	@Query("SELECT max(entity.id) FROM ICProductCustomerPrices entity")
	Long findMaxId();

	@Query("SELECT entity FROM ICProductCustomerPrices entity"
			+ " WHERE entity.customer.id = ?1" + " AND entity.priceToDate >= ?2" + " AND entity.product.status = ?3"
			+ " AND entity.status = ?4")
	List<ICProductCustomerPrices> findByCustomerIdAndPriceTo(Long customerId, DateTime now, String productStatus,
			String status);
	
	List<ICProductCustomerPrices> findByStatusAndCustomerIdAndProductStatusAndPriceToDateGreaterThanEqual(String status, Long customerId,String productStatus, DateTime now);
}
