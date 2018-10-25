package com.bys.crm.domain.erp.constant;

public enum ContactItem {
	title("Danh xưng"),
	birthday("Ngày sinh"),
	firstName("Họ*"),
	lastName("Tên*"),
	company("Công ty"),
	email("Email"),
	potentialSource("Nguồn tiềm năng"),
	jobTitle("Chức danh"),
	department("Phòng ban"),
	phone("Điện thoại công ty"),
	cellularPhone("Điện thoại di động"),
	homePhone("Điện thoại nhà riêng"),
	secondaryPhone("Điện thoại phụ"),
	assistant("Trợ lý"),
	assistantPhone("Điện thoại trợ lý"),
	employeeNo("Phân công cho*"),
	address("Địa chỉ"),
	district("Quận"),
	city("Thành phố"),
	country("Quốc gia"),
	information("Mô tả"),
	branch("Chi nhánh");

	private String dBCode;

	private ContactItem(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ContactItem fromValue(String value) {
		for (ContactItem status : ContactItem.values()) {
			if (status.value().equalsIgnoreCase(value == null ? null : value.trim())) {
				return status;
			}
		}

		return null;
	}

	public static ContactItem fromName(String name) {
		for (ContactItem status : ContactItem.values()) {
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
