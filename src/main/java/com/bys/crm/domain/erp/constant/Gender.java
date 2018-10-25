package com.bys.crm.domain.erp.constant;

public enum Gender {
	Male("Male"),
	Female("Female");

	private String dBCode;

	private Gender(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static Gender fromValue(String value) {
		 
		for (Gender status : Gender.values()) {
			if (status.value().equalsIgnoreCase(value.isEmpty()?null:value)) {
				return status;
			}
		}

		return null;
	}

	public static Gender fromName(String name) {
		for (Gender status : Gender.values()) {
			if (status.name().equalsIgnoreCase(name.isEmpty()?null:name)) {
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
