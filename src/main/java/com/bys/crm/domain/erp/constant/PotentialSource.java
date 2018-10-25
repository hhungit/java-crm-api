package com.bys.crm.domain.erp.constant;

public enum PotentialSource {
	Customers("Khách hàng cũ"),
	AutomaticallyEnterTheSystem("Tự động nhập vào hệ thống"),
	IntroduceStaff("Nhân viên giới thiệu"),
	Partner("Đối tác"),
	PublicRelations("Quan hệ công chúng "),
	DirectMail("Thư trực tiếp"),
	Seminor("Hội thảo"),
	Exhibition("Triển lãm"),
	webpage("Trang web"),
	WordOfMouth("Truyền miệng"),
	OtherSouces("Nguồn khác");
	
	private String dBCode;

	private PotentialSource(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static PotentialSource fromValue(String value) {
		for (PotentialSource status : PotentialSource.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static PotentialSource fromName(String name) {
		for (PotentialSource status : PotentialSource.values()) {
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
