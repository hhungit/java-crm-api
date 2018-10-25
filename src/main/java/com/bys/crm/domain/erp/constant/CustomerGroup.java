package com.bys.crm.domain.erp.constant;

public enum CustomerGroup {
	New("Mới"),
	Care("Quan tâm"),
	Intimate("Thân thiết");
	
	private String dBCode;

	private CustomerGroup(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CustomerGroup fromValue(String value) {
		for (CustomerGroup status : CustomerGroup.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static CustomerGroup fromName(String name) {
		for (CustomerGroup status : CustomerGroup.values()) {
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
