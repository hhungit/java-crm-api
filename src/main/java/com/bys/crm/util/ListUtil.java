package com.bys.crm.util;

import java.util.ArrayList;
import java.util.Set;

import com.bys.crm.domain.erp.constant.AAStatus;
import com.bys.crm.domain.erp.model.BRBranchs;
import com.bys.crm.domain.erp.model.HRGroups;

public class ListUtil {
	public static Integer[] convertToArrayId(ArrayList<Integer> ids ) {
		return ids.isEmpty() ? null : ids.toArray(new Integer[0]);
	}
	
	public static Integer[] convertToArrayId(Set<BRBranchs> branchs) {
		ArrayList<Integer> ids = new ArrayList<>();
		branchs.forEach(branch -> {
			if (branch.getId() != null && AAStatus.Alive.name().equals(branch.getStatus())) {
				ids.add(branch.getId());
			}
		});
		return ids.isEmpty() ? null : ids.toArray(new Integer[0]);
	}

	public static ArrayList<Integer> getGroupIds(Set<HRGroups> groups) {
		ArrayList<Integer> groupIds = new ArrayList<>();

		groups.forEach(item -> {
			groupIds.add(item.getId());
		});

		return groupIds;
	}
	
	public static Boolean isAssignedToEmployee(Integer employeeId,
			ArrayList<Integer> groupIds, Integer entityGroupId, Integer entityEmployeeId) {
		if(entityEmployeeId!=null) {
			return entityEmployeeId.intValue() == employeeId.intValue();
		}else if(entityGroupId!=null) {
			return groupIds.contains(entityGroupId.intValue());
		}else {
			return false;
		}
	}
	
	public static Boolean isAssignedToEmployee(Integer employeeId,
			ArrayList<Integer> groupIds, Integer entityGroupId, Integer entityEmployeeId, Boolean isLeader) {
		if( isLeader) {
			return true;
		}else {
			if(entityEmployeeId!=null) {
				return entityEmployeeId.intValue() == employeeId.intValue();
			}else if(entityGroupId!=null) {
				return groupIds.contains(entityGroupId.intValue());
			}else {
				return false;
			}
		}
		
	}
}
