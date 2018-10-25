package com.bys.crm.domain.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.repository.BRBranchsRepository;

@Service
public class BranchService {
	
	@Autowired
	private BRBranchsRepository branchsRepository;
	
	public BRBranchs getBranch(Integer branchId) {
		return branchsRepository.findByStatusAndId(AAStatus.Alive.name(), branchId);
	}
}
