package com.bys.crm.domain.erp.constant;

public enum CustomerType {
	Individual("Individual"),
	Company("Company"),
	Personal("Personal");

	private String dBCode;

	private CustomerType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CustomerType fromName(String name) {
		for (CustomerType status : CustomerType.values()) {
			if (status.name().equalsIgnoreCase(name)) {
				return status;
			}
		}

		return null;
	}

	public static String supportValues() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < values().length; i++) {
			String delimeter = (i == 0) ? "" : ",";
			builder.append(delimeter).append(values()[i].name());
		}
		return builder.toString();
	}
}
