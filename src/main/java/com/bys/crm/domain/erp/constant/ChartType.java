package com.bys.crm.domain.erp.constant;

import java.util.Arrays;

public enum ChartType {
	DAY("day"),
	MONTH("month"),	
	YEAR("year");
	
	private String dBCode;

	private ChartType(String dBCode) {
		this.dBCode = dBCode;
	}

	public String value() {
		return this.dBCode;
	}

	public static ChartType fromValue(String value) {
		return Arrays.asList(ChartType.values()).stream().filter(item -> item.name().equalsIgnoreCase(value))
				.findFirst().orElse(null);
	}

}
