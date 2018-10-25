package com.bys.crm.domain.erp.constant;

public enum Name {
	He("Anh"),
	She("Chị"),
	Sir("Ông"),
	Madam("Bà"),
	Doctor("Tiến sỹ"),
	Professor("Giáo sư");
	
	private String dBCode;

	private Name(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static Name fromValue(String value) {
		for (Name status : Name.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static Name fromName(String name) {
		for (Name status : Name.values()) {
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
