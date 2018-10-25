package com.bys.crm.util;

public class SecurityUtil {
	
	private SecurityUtil() {
		
	}
	
	public static String getLoginName(String userName, String deviceKey, String deviceType) {
		StringBuilder builder = new StringBuilder(userName);
		if (deviceKey != null && deviceType != null) {
			builder.append("#").append(deviceKey).append("#").append(deviceType);
		}
		return builder.toString();
	}

}
