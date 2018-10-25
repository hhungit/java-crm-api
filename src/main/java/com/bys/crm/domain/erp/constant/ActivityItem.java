package com.bys.crm.domain.erp.constant;

public enum ActivityItem {
	name("Tiêu đề*"),
	activityType("Loại hoạt động"),
	startDate("Ngày giờ bắt đầu*"),
	endDate("Ngày giờ kết thúc*"),
	activityStatus("Trạng thái*"),
	eventType("Loại sự kiện"),
	address("Vị trí"),
	activityObjectType("Liên quan đến loại"),
	activityObjectTypeName("Cụ thể liên quan"),
	description("Mô tả"),
	employeeNo("Phân công cho*"),
	branch("Chi nhánh");

	private String dBCode;

	private ActivityItem(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ActivityItem fromValue(String value) {
		for (ActivityItem status : ActivityItem.values()) {
			if (status.value().equalsIgnoreCase(value == null ? null : value.trim())) {
				return status;
			}
		}

		return null;
	}

	public static ActivityItem fromName(String name) {
		for (ActivityItem status : ActivityItem.values()) {
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
