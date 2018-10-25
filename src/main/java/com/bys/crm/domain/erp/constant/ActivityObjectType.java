package com.bys.crm.domain.erp.constant;

public enum ActivityObjectType {
	Prospect("Tiềm năng"),
	Customer("Khách hàng"),
	Opportunity("Cơ hội"),
	Contact("Liên hệ"),
	Campaign("Chiến dịch");
	
	private String dBCode;

	private ActivityObjectType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ActivityObjectType fromValue(String value) {
		for (ActivityObjectType status : ActivityObjectType.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static ActivityObjectType fromName(String name) {
		for (ActivityObjectType status : ActivityObjectType.values()) {
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
