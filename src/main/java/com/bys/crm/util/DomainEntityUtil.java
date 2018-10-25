package com.bys.crm.util;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

public class DomainEntityUtil {
	private DomainEntityUtil() {
		
	}
	
	public static String getDatabaseColumnName(Field field) {
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			return column.name();
		}
		JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
		if (joinColumn != null) {
			return joinColumn.name();
		}
		
		return field.getName();
		
	}
	
	

}
