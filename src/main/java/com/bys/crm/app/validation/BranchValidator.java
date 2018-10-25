package com.bys.crm.app.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.constant.ErrorCodeEnum;
import com.bys.crm.app.exception.InvalidException;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.service.BranchService;

@Component
public class BranchValidator {

	@Autowired
	private BranchService branchService;
	
	/**
	 * Validation branchId
	 * 
	 * @param branchId
	 * @return BRBranchs
	 */
	public BRBranchs validateBranchId(Integer branchId) {
		BRBranchs branch = branchService.getBranch(branchId);
		if (branch == null) {
			throw new InvalidException("Branch id is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
		}

		return branch;
	}
}
