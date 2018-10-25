package com.bys.crm.domain.erp.constant;

public enum GELocationType {
	PROVINCE("Province/City"),
	DISTRICT("District");
	
	
	private String dBCode;

	private GELocationType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static GELocationType fromValue(String value) {
		for (GELocationType type : GELocationType.values()) {
			if (type.value().equalsIgnoreCase(value)) {
				return type;
			}
		}

		return null;
	}
}
