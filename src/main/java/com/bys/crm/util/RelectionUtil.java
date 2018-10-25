package com.bys.crm.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelectionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RelectionUtil.class);
	
	public static Class<?> getClassParameterType (Class<?> clazz, Integer index) {
		Class<?> result = null;
		Type type = clazz.getGenericSuperclass();

		if(type instanceof ParameterizedType){
			Type[] fieldArgTypes = ((ParameterizedType) type).getActualTypeArguments();
			result = (Class<?>) fieldArgTypes[index];
		}

		return result;
	}
	
	public static Object getFieldValue(Object object, Field field) {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
			Method method = propertyDescriptor.getReadMethod();
			return method.invoke(object);
		} catch (Exception e) {
			boolean isAccessible = field.isAccessible();
			field.setAccessible(true);
			try {
				return field.get(object);
			} catch (Exception e1) {
				LOGGER.error("Error getting value of field {} in object {}", field.getName(),
						object.getClass().getSimpleName(), e1);
			} 
			finally {
				field.setAccessible(isAccessible);
			}
			
			return null;
		}

	}
}
