package com.bys.crm.domain.erp.constant;

public enum PotentialStatus {
	TryToContact("Cố gắng liên hệ"),
	Indifferent("Không quan tâm"),
	ContactInTheFuture("Liên hệ trong tương lai"),
	Contacted("Đã liên hệ"),
	Care("Quan tâm"),
	NoLongerAvailable("Không còn sử dụng được"),
	PotentialIsLost("Tiềm năng đã mất"),
	NotContact("Không liên hệ được"),
	Qualified("Đạt yêu cầu"),
	Enthusiasm("Nhiệt tình");
	
	private String dBCode;

	private PotentialStatus(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static PotentialStatus fromValue(String value) {
		for (PotentialStatus status : PotentialStatus.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static PotentialStatus fromName(String name) {
		for (PotentialStatus status : PotentialStatus.values()) {
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
