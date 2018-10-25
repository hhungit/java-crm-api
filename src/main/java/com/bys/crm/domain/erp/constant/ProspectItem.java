package com.bys.crm.domain.erp.constant;

public enum ProspectItem {
	title("Danh xưng"),
	potentialStatus("Trạng thái của tiềm năng"),
	firstName("Họ và tên*"),
	lastName("Họ*"),
	phone("Điện thoại chính *"),
	cellPhone("Điện thoại di động"),
	email("Email"),
	website("Website"),
	company("Tên công ty"),
//	potentialSource("Nguồn tiềm năng"),
	address("Địa chỉ"),
	rate("Đánh giá tiềm năng"),
	description("Mô tả chi tiết"),
	business("Lĩnh vực hoạt động"),
	district("Quận"),
	city("Thành phố"),
	country("Quốc gia"),
//	revenue("Doanh thu hàng năm"),
	branch("Chi nhánh"),
	employeeNo("Phân công cho*"),
	customerResource("Nguồn tiềm năng"),
	campaignNo("Chiến dịch");

	private String dBCode;

	private ProspectItem(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ProspectItem fromValue(String value) {
		for (ProspectItem status : ProspectItem.values()) {
			if (status.value().equalsIgnoreCase(value == null ? null : value.trim())) {
				return status;
			}
		}

		return null;
	}

	public static ProspectItem fromName(String name) {
		for (ProspectItem status : ProspectItem.values()) {
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
