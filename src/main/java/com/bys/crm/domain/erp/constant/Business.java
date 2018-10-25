package com.bys.crm.domain.erp.constant;

public enum Business {
	Clothes("Quần áo"),
	Bank("Ngân hàng"),
	Biotechnology("Công nghệ sinh học"),
	Chemistry("Hóa học"),
	Communication("Giao tiếp"),
	Build("Xây dựng"),
	Advisory("Tư vấn"),
	Education("Giáo dục"),
	Electronics("Hàng điện tử"),
	Energy("Năng lượng"),
	Technique("Kỹ thuật"),
	Entertainment("Giải trí"),
	Environment("Môi trường"),
	Accountant("Kế toán"),
	FoodAndBeverages("Thực phẩm và nước giải khát"),
	Goverment("Chính phủ"),
	HealthCare("Chăm sóc sức khỏe"),
	Hotel("Khách sạn"),
	Insurrance("Bảo hiểm"),
	Machines("Máy móc"),
	Manufacturing("Chế tạo"),
	Media("Phương tiện truyền thông"),
	Nonprofit("Phi lợi nhuận"),
	Retail("Bán lẻ"),
	Transport("Vận chuyển"),
	Technology("Công nghệ"),
	Telecommunication("Viễn thông"),
	Utilities("Tiện ích"),
	OrtherSouces("Nguồn khác");
	
	private String dBCode;

	private Business(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static Business fromValue(String value) {
		for (Business status : Business.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static Business fromName(String name) {
		for (Business status : Business.values()) {
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
