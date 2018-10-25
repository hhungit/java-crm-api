package com.bys.crm.domain.erp.constant;

public enum ADObjectType {
	Customer("customer"),
	Employee("employee"),
	Prospect("prospect"),
	Contact("contact"),
	Opportunity("opportunity"),
	Campaign("campaign"),
	CRMCampaign("CRMCampaign"),
	Activity("activity"),
	Event("event"),
	Work("work");
	
	private String dBCode;

	private ADObjectType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ADObjectType fromValue(String value) {
		for (ADObjectType status : ADObjectType.values()) {
			if (status.value().equalsIgnoreCase(value)) {
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
