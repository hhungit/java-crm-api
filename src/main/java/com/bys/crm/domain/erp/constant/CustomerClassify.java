package com.bys.crm.domain.erp.constant;

public enum CustomerClassify {
	Analysis("Phân tích"),
	Competitor("Đối thủ"),
	Partner("Đối tác"),
	Integrator("Người phân tích"),
	Investor("Người đầu tư"),
	Prospect("Khách hàng tiềm năng"),
	Distributor("Nhà phân phối"),
	OtherSouces("Nguồn khác");
	
	private String dBCode;

	private CustomerClassify(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CustomerClassify fromValue(String value) {
		for (CustomerClassify status : CustomerClassify.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static CustomerClassify fromName(String name) {
		for (CustomerClassify status : CustomerClassify.values()) {
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
