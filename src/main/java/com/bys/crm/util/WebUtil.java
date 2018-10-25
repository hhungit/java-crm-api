package com.bys.crm.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {
	  private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);
	private  WebUtil() {
		super();
	}
	
	public static String readRequestBody(HttpServletRequest request) {
		StringBuilder jb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) 
				jb.append(line);
        } catch (Exception e) {
            LOGGER.warn("Error reading request body", e);
        }

        return jb.toString();
	}

}
