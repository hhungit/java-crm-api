package com.bys.crm.domain.erp.constant;

public enum EventType {
	Meeting("Meeting"),
	Call("Gọi điện thoại");
	
	private String dBCode;

	private EventType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static EventType fromValue(String value) {
		for (EventType status : EventType.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static EventType fromName(String name) {
		for (EventType status : EventType.values()) {
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
