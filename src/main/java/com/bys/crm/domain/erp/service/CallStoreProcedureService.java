package com.bys.crm.domain.erp.service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

@Repository
public class CallStoreProcedureService {

	@PersistenceContext
	private EntityManager entityManager;

	public Long getInitBalance(String accountNo, Long customerID, String objetcType) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACAccounts_GetAccountInitialBalance_CRM")
				.registerStoredProcedureParameter("ACAccountNo", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectID", Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ACObjectType", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("InitAmount", Long.class, ParameterMode.OUT)
				.setParameter("ACAccountNo", accountNo)
				.setParameter("ACObjectID", customerID)
				.setParameter("ACObjectType", objetcType);

		query.execute();

		Long initBalance = (Long) query.getOutputParameterValue("InitAmount");

		return initBalance;
	}
}
