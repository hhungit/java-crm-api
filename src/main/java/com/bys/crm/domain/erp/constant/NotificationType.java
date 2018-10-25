package com.bys.crm.domain.erp.constant;

import java.util.Arrays;

public enum NotificationType {
	Campaign("Chiến dịch "),
	Event("Sự kiện "),
	Work("Công việc ");
	
	private String dBCode;

	private NotificationType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static NotificationType fromValue(String value) {
		return Arrays.asList(NotificationType.values()).stream().filter(item -> item.name().equalsIgnoreCase(value))
				.findFirst().orElse(null);
	}
}
