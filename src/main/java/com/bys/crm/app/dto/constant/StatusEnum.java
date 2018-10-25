package com.bys.crm.app.dto.constant;

import java.util.Arrays;

public enum StatusEnum {
	ALL, NEW, CHANGED;

	public static StatusEnum fromValue(String value) {
		return Arrays.asList(StatusEnum.values()).stream().filter(item -> item.name().equalsIgnoreCase(value)).findAny()
				.orElse(null);
	}
}
