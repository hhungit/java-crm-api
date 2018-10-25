package com.bys.crm.domain.erp.constant;

public enum ActivityType {
	Work("work"),
	Event("event");
	
	private String dBCode;

	private ActivityType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ActivityType fromValue(String value) {
		for (ActivityType status : ActivityType.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static ActivityType fromName(String name) {
		for (ActivityType status : ActivityType.values()) {
			if (status.name().equalsIgnoreCase(name)) {
				return status;
			}
		}

		return null;
	}

	public static String supportValues() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < values().length; i++) {
			String delimeter = (i == 0)?"":",";
			builder.append(delimeter).append(values()[i].name());
		}
		return builder.toString();
	}
}
