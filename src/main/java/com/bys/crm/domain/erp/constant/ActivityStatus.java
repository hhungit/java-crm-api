package com.bys.crm.domain.erp.constant;

public enum ActivityStatus {
	New("Sẽ thực hiện"),
	Inprogress("Đang thực hiện"),
	Completed("Đã hoàn thành"),
	Issued("Gặp khó khăn"),
	Canceled("Đã hủy"),
	Auditing("Đang kiểm tra");
	
	private String dBCode;

	private ActivityStatus(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ActivityStatus fromValue(String value) {
		for (ActivityStatus status : ActivityStatus.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static ActivityStatus fromName(String name) {
		for (ActivityStatus status : ActivityStatus.values()) {
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
