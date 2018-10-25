package com.bys.crm.domain.erp.constant;

public enum OpportunityClassify {
	OldCustomers("Khách hàng cũ"),
	NewCustomers("Khách hàng mới");
	
	private String dBCode;

	private OpportunityClassify(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static OpportunityClassify fromValue(String value) {
		for (OpportunityClassify status : OpportunityClassify.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static OpportunityClassify fromName(String name) {
		for (OpportunityClassify status : OpportunityClassify.values()) {
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
