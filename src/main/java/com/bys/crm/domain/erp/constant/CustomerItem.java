package com.bys.crm.domain.erp.constant;

public enum CustomerItem {
	name("Tên khách hàng*"),
	website("Website"),
	fax("Fax"),
	tel1("Điện thoại chính"),
	tel2("Điện thoại phụ"),
	stockCode("Mã chứng khoán"),
	taxNumber("Mã số thuế"),
	email("Email chính"),
	email2("Email phụ"),
	business("Ngành nghề"),
	group("Nhóm"),
	evaluate("Đánh giá"),
	classify("Phân loại"),
	revenueDueYear("Danh thu hàng năm"),
	employeeNo("Phân công cho*"),
	address("Địa chỉ"),
	district("Quận"),
	city("Thành phố"),
	country("Quốc gia"),
	information("Mô tả chi tiết"),
	branch("Chi nhánh"),
	customerType("Loại khách hàng*"),
	companyEstablishmentDay("Ngày thành lập công ty"),
	dob("Ngày sinh nhật");

	private String dBCode;

	private CustomerItem(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CustomerItem fromValue(String value) {
		for (CustomerItem status : CustomerItem.values()) {
			if (status.value().equalsIgnoreCase(value == null ? null : value.trim())) {
				return status;
			}
		}

		return null;
	}

	public static CustomerItem fromName(String name) {
		for (CustomerItem status : CustomerItem.values()) {
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
