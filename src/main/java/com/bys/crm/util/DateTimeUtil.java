package com.bys.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateTimeUtil {

	private DateTimeUtil() {

	}

	public static Date toDate(String value, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(value + " cannot parse to format " + format);
		}
	}

	public static String toString(Date value, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(value);
	}

	public static DateTime toDateTimeAtStartOfDay(Long value) {
		try {
			if (value != null) {
				return new DateTime(value).withTimeAtStartOfDay();
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(value + " cannot parse to datetime ");
		}
		return null;
	}

	public static DateTime toDateTimeAtEndOfDay(Long value) {
		try {
			if (value != null) {
				return new DateTime(value).withTime(23, 59, 59, 999);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(value + " cannot parse to datetime ");
		}
		return null;
	}
}
