package com.bys.crm.domain.erp.constant;

public enum CampaignStatus {
	IsPlanning_Campaign("Đang lên kế hoạch"),
	Proceed_Campaign("Tiến hành"),
	DoNotProceed("Hoãn lại"),
	Finish_Campaign("Hoàn thành"),
	Cancel("Hủy bỏ");

	private String dBCode;

	private CampaignStatus(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static CampaignStatus fromValue(String value) {
		for (CampaignStatus status : CampaignStatus.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static CampaignStatus fromName(String name) {
		for (CampaignStatus status : CampaignStatus.values()) {
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
