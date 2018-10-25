package com.bys.crm.domain.erp.constant;

public enum OpportunityStep {
	Survey("Khảo sát (10%)"),
	Optional("Lựa chọn (20%)"),
	DemandAnalysis("Phân tích nhu cầu (25%)"),
	RecommendationsMade("Đề nghị thực hiện (30%)"),
	IdentificationWithManufacturer("Xác định với NSX (40%)"),
	ChangeFeedback("Sửa đổi feedback (50%)"),
	QuatationsForCustomers("Báo giá (65%)"),
	Agreement("Thỏa thuận (85%)"),
	Success("Thành công (100%)"),
	Failure("Thất bại (0%)");
	
	private String dBCode;

	private OpportunityStep(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static OpportunityStep fromValue(String value) {
		for (OpportunityStep status : OpportunityStep.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static OpportunityStep fromName(String name) {
		for (OpportunityStep status : OpportunityStep.values()) {
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
