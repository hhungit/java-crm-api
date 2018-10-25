package com.bys.crm.domain.erp.constant;

public enum ADConfigKeyGroup {
	ProspectStatus("prospect-status"),
	ProspectCustomerSource("prospect-sources"),
	ProspectCustomerEvaluation("prospect-evaluations"),
	ProspectCustomerName("prospect-names"),
	ProspectCustomerBusiness("prospect-business"),
	CustomerGroupCRM("customer-groups"),
	CustomerClassify("customer-classifies"),
	OpportunityClassify("opportunity-classifies"),
	CampaignStatus("campaign-status"),
	CampaignType("campaign-types"),
	ActivityStatus("activity-status"),
	ActivityType("activity-types"),
	InvoiceStatus("invoice-status"),
	EventType("event-types"),
	OpportunityStep("opportunity-steps"),
	CoordinatorStatus("coordinator-status"),
	TaskStatus("task-status");
	
	private String dBCode;

	private ADConfigKeyGroup(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ADConfigKeyGroup fromValue(String value) {
		for (ADConfigKeyGroup status : ADConfigKeyGroup.values()) {
			if (status.value().equalsIgnoreCase(value)) {
				return status;
			}
		}

		return null;
	}

	public static ADConfigKeyGroup fromName(String name) {
		for (ADConfigKeyGroup status : ADConfigKeyGroup.values()) {
			if (status.name().equalsIgnoreCase(name)) {
				return status;
			}
		}

		return null;
	}

	public static String supportValues() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < values().length; i++) {
			String delimeter = (i == 0) ? "" : ",";
			builder.append(delimeter).append(values()[i].name());
		}
		return builder.toString();
	}

}
