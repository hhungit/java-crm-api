package com.bys.crm.domain.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.bys.crm.app.dto.MyObject;
import com.bys.crm.domain.erp.repository.TestCallStoreProdecureRepository;

@Service
public class TestCallStoreProdecureService implements TestCallStoreProdecureRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MyObject> getSomeLegacyData(String firstParameter) {
		StoredProcedureQuery storedProcedure = entityManager
				.createStoredProcedureQuery("ACAccounts_GetAccountInitialBalance");

		// String firstParameterN = "131";
		// Set the parameters of the stored procedure.
		String firstParam = "ACAccountNo";
		String secondParam = "ACObjectID";
		String objectType = "ACObjectType";
		String currencyID = "GECurrencyID";
		String companyBankID = "CSCompanyBankID";
		storedProcedure.registerStoredProcedureParameter(firstParam, String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(secondParam, Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(objectType, String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(currencyID, Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(companyBankID, Integer.class, ParameterMode.IN);

		storedProcedure.setParameter(firstParam, "131");
		storedProcedure.setParameter(secondParam, 2);
		storedProcedure.setParameter(objectType, "");
		storedProcedure.setParameter(currencyID, 0);
		storedProcedure.setParameter(companyBankID, 0);

		storedProcedure.execute();
		// Call the stored procedure.
		List<Object[]> storedProcedureResults = storedProcedure.getResultList();

		System.out.println("AAAA");
		// Use Java 8's cool new functional programming paradigm to map the
		// objects from the stored procedure results
		return storedProcedureResults.stream().map(result -> new MyObject((Integer) result[0], (Integer) result[1]))
				.collect(Collectors.toList());
	}

	public Long testCountCustomer() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("count_comments")
				.registerStoredProcedureParameter("postId", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("commentCount", Long.class, ParameterMode.OUT)
				.setParameter("postId", 2L);

		query.execute();

		Long commentCount = (Long) query.getOutputParameterValue("commentCount");

		return commentCount;
	}
	
	public List<Object[]>  testCountCustomerList() {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("post_comments_list")
				.registerStoredProcedureParameter("postId", Long.class, ParameterMode.IN)
				.setParameter("postId", 2L);

		query.execute();
		List<Object[]> postComments = query.getResultList();

		return postComments;
	}
	
	public BigDecimal getInitBalance(String accountNo, Long customerID, String objetcType) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACAccounts_GetAccountInitialBalance_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("InitAmount", BigDecimal.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerID)
				.setParameter("ACObjectType", objetcType);

		query.execute();

		BigDecimal initBalance = (BigDecimal) query.getOutputParameterValue("InitAmount");

		return initBalance;
	}
	
	public BigDecimal getDebitAmount(Long customerId, String accountNo, String objectType, String fromDate) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACDocumentEntrys_GetAccountDebitAmount_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("FromDate", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ExchangeAmount", BigDecimal.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerId)
				.setParameter("ACObjectType", objectType)
				.setParameter("FromDate", fromDate);

		query.execute();

		BigDecimal debitAmount = (BigDecimal) query.getOutputParameterValue("ExchangeAmount");

		return debitAmount;
	}
	
	public BigDecimal getCreditAmount(Long customerId, String accountNo, String objectType, String fromDate) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACDocumentEntrys_GetAccountCreditAmount_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("FromDate", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ExchangeAmount", BigDecimal.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerId)
				.setParameter("ACObjectType", objectType)
				.setParameter("FromDate", fromDate);

		query.execute();

		BigDecimal creditAmount = (BigDecimal) query.getOutputParameterValue("ExchangeAmount");

		return creditAmount;
	}
	
	public BigDecimal getStartBalance(Long customerId, String accountNo, String objectType, String fromDate) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACDocumentEntrys_GetAccountDebtAmountStartPeriod_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("FromDate", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("DebtAmount", BigDecimal.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerId)
				.setParameter("ACObjectType", objectType)
				.setParameter("FromDate", fromDate);

		query.execute();

		BigDecimal startBalance = (BigDecimal) query.getOutputParameterValue("DebtAmount");

		return startBalance;
	}
	
	public BigDecimal getEndBalance(Long customerId, String accountNo, String objectType, String toDate) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACDocumentEntrys_GetAccountDebtAmountEndPeriod_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ToDate", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("DebtAmount", BigDecimal.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerId)
				.setParameter("ACObjectType", objectType)
				.setParameter("ToDate", toDate);

		query.execute();

		BigDecimal endBalance = (BigDecimal) query.getOutputParameterValue("DebtAmount");

		return endBalance;
	}
}
