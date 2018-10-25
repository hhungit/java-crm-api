package com.bys.crm.domain.erp.constant;

public enum PotentialRate {
	Potential("Tiềm năng"),
	Nomal("Bình thường"),
	NonPotential("Không tiềm năng");
	
	private String dBCode;

	private PotentialRate(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static PotentialRate fromValue(String value) {
		for (PotentialRate status : PotentialRate.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static PotentialRate fromName(String name) {
		for (PotentialRate status : PotentialRate.values()) {
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
