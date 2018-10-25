package com.bys.crm.domain.erp.constant;

public enum ADPrivilegeName {
	Add("Add"),
	Edit("Edit"),
	Delete("Delete"),
	View("View");
	
	private String dBCode;

	private ADPrivilegeName(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ADPrivilegeName fromValue(String value) {
		for (ADPrivilegeName status : ADPrivilegeName.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static ADPrivilegeName fromName(String name) {
		for (ADPrivilegeName status : ADPrivilegeName.values()) {
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
