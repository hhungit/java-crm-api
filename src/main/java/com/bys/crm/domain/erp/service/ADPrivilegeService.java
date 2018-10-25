//package com.bys.crm.domain.erp.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bys.crm.app.dto.PrivilegeDetailDto;
//import com.bys.crm.app.dto.constant.ErrorCodeEnum;
//import com.bys.crm.app.exception.InvalidException;
//import com.bys.crm.app.mapping.GenericMapper;
//import com.bys.crm.domain.erp.constant.AAStatus;
//import com.bys.crm.domain.erp.model.ADPrivilegeDetails;
//import com.bys.crm.domain.erp.model.ADPrivileges;
//import com.bys.crm.domain.erp.model.HREmployees;
//import com.bys.crm.domain.erp.repository.HREmployeesRepository;
//import com.bys.crm.domain.erp.model.ADPrivilegeGroups;
//
//@Service
//public class ADPrivilegeService {
//
//	@Autowired
//	private HREmployeesRepository employeesRepository;
//
//	@Autowired
//	private GenericMapper mapper;
//	
//	public boolean checkPrivilege(Set<ADPrivilegeGroups> privilegeGroups, String privilege, String url) {
//		for (ADPrivilegeGroups privilegeGroup : privilegeGroups) {
//			for (ADPrivileges priv : privilegeGroup.getPrivileges()) {
//				for (ADPrivilegeDetails detail : priv.getDetails()) {
//					if (privilege.equals(priv.getName()) && url.equals(detail.getValue())) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	public List<PrivilegeDetailDto> getPrivilegeDetailList(Integer employeeId) {
//		HREmployees entity = employeesRepository.findByStatusAndId(AAStatus.Alive.name(), employeeId);
//		if (entity == null) {
//			throw new InvalidException("Employee id is not exist.", ErrorCodeEnum.DATA_NOT_EXIST);
//		}
//
//		List<ADPrivilegeDetails> privilegeDetails = new ArrayList<>();
//		for (ADPrivilegeGroups privilegeGroup : entity.getPrivilegeGroups()) {
//			for (ADPrivileges priv : privilegeGroup.getPrivileges()) {
//				for (ADPrivilegeDetails detail : priv.getDetails()) {
//					privilegeDetails.add(detail);
//				}
//			}
//		}
//		return mapper.buildObjects(privilegeDetails, PrivilegeDetailDto.class);
//	}
//}
