package com.bys.crm.domain.erp.constant;

public enum CampaignType {
	Seminor("Hội thảo"),
	Webinars("Chuyên đề trên web"),
	Exhibition("Triển lãm"),
	IntroductionProgram(" chương Trình giới thiệu"),
	PublicRelations("Quan hệ công chúng"),
	Advertisement("Quảng cáo"),
	BannerAdvertising("Quảng cáo trên banner"),
	DirectMail("Gửi thư trực tiếp"),
	EmailMarketing("Tiếp thị qua Email"),
	Telemarketing("Tiếp thị qua điện thoại"),
	Other("Khác");
	
	private String dBCode;

	private CampaignType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CampaignType fromValue(String value) {
		for (CampaignType status : CampaignType.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static CampaignType fromName(String name) {
		for (CampaignType status : CampaignType.values()) {
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
