package com.bys.crm.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	public static boolean isNotEmpty(String value) {
		return value != null && value.trim().length() > 0;
	}

	public static String convertSearchKey(String value) {
		return (value == null || value.trim().length() == 0) ? "%"
				: StringUtils.trim(value);
	}
	public static String convertSearchKeyLike(String value) {
		return (value == null || value.trim().length() == 0) ? "%"
				: "%" + StringUtils.trim(value)+"%";
	}

	public static Integer convertSearchKey(Integer key) {
		return (key == null) ? 0 : key;
	}
}
