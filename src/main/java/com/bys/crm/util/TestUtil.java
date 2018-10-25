package com.bys.crm.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.TargetSource;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);
    private static final Object[] NO_PARAMETER = {};

    private TestUtil() {

    }
    @SuppressWarnings("unchecked")
    public static <T> T generateDtoObject(Class<T> type) {

        try {
            //Constructor<?> constructor = type.getDeclaredConstructors()[0];
            Constructor<?>[] constructors = type.getDeclaredConstructors();            
            T object = null;
            
            if (constructors.length > 0) {
                Constructor<?> constructor = findSimpleConstructor(constructors);
                boolean isAccessible = constructor.isAccessible();
                constructor.setAccessible(true);
                
                if (constructor.getParameterCount() == 0) {
                    object = (T) constructor.newInstance(NO_PARAMETER);
                }
                else {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    List<Object> parameters = new ArrayList<>();
                    Arrays.stream(parameterTypes).forEach(parameterType -> parameters.add(getFieldValue(parameterType)));
                    object = (T) constructor.newInstance(parameters.toArray());
                }
                constructor.setAccessible(isAccessible);
                for (Field field : type.getDeclaredFields()) {
                	JsonIgnore ignore = field.getAnnotation(JsonIgnore.class);
                	if (ignore != null) {
                		continue;
                	}
                    isAccessible = field.isAccessible();
                    field.setAccessible(true);
                    if (!Modifier.isFinal(field.getModifiers())) {
                        Object value = addDummyValue(object, field);
                        if (value == null) {
                            field.set(object, generateDtoObject(field.getType()));
                        }
                    }
                    field.setAccessible(isAccessible);
                }
             
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException
                | SecurityException | InvocationTargetException e) {
            LOGGER.error("Cannot create Dto object " + type.getSimpleName(), e);
        }

        return null;
    }
    
    private static Constructor<?> findSimpleConstructor(Constructor<?>[] constructors) {
        List<Constructor<?>> simpleConstructors = new ArrayList<Constructor<?>>();
        for (Constructor<?> constructor: constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor;
            }
            if (constructor.getParameterCount() == 1) {
                Class<?> paramType = constructor.getParameterTypes()[0];
                try {
                    boolean defaultPackage = paramType.getPackage() != null && paramType.getPackage().equals(String.class.getPackage());
                    if (paramType.isPrimitive() || defaultPackage) {
                        simpleConstructors.add(constructor);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error proccesing paramType" + paramType.getName(), e);
                }
            }
        }
        return simpleConstructors.isEmpty() ? constructors[0] : simpleConstructors.get(0);
    }

    private static Object getFieldValue(Class<?> type) {
        Object value = null;
       
        if (Integer.class.isAssignableFrom(type) || type.getName().equals("int")) {
            value = Integer.valueOf(1);
        } else if (Long.class.isAssignableFrom(type) || type.getName().equals("long")) {
            value = Long.valueOf(1L);
        } else if (Short.class.isAssignableFrom(type) || type.getName().equals("short")) {
            value = Short.valueOf("1");
        } else if (Float.class.isAssignableFrom(type) || type.getName().equals("float")) {
            value = Float.valueOf(1f);
        } else if (Double.class.isAssignableFrom(type) || type.getName().equals("double")) {
            value = Double.valueOf(1d);
        } else if ( Byte.class.isAssignableFrom(type) || type.getName().equals("byte")) {
            value = Byte.valueOf("1");
        } else if (Boolean.class.isAssignableFrom(type) || type.getName().equals("boolean")) {
            value = true;
        } else if (UUID.class.isAssignableFrom(type)) {
            value = UUID.randomUUID();
        }else if (Date.class.isAssignableFrom(type)) {
            value = new Date();
        } else if (DateTime.class.isAssignableFrom(type)) {
            value = new DateTime();
        } else if (String.class.isAssignableFrom(type)) {
            value = "S";
        } else if (type.isEnum()) {
            if (type.getEnumConstants().length > 0) {
                value = type.getEnumConstants()[0];
               
            } else {
                try {
                    value = type.getConstructor(String.class).newInstance("ENUM");
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
                    LOGGER.warn("Cannot generate enum value of enum " + type.getSimpleName());
                }
            }
        } else if (Collection.class.isAssignableFrom(type)) {
            try {
                if (Set.class.isAssignableFrom(type)) {
                    value = new HashSet<>();
                } else if (Map.class.isAssignableFrom(type)) {
                    value = Collections.EMPTY_MAP;
                } else if (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type)) {
                    value = Collections.EMPTY_LIST;
                } else {
                    value = type.newInstance();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Cannot create empty object of type " + type.getSimpleName(), e);
            }
        }
        return value;
    }

    private static Object addDummyValue(Object object, Field field) throws IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

        Object currentValue = field.get(object);
        if (currentValue != null) {
            return currentValue;
        }
        Object value = getFieldValue(field.getType());
        if (field.getType() == String.class && isDateField(field)) {
            value = new DateTime().toString();
        }
     
        // Use setter method instead of set field value directly to have test
        // coverage

        String name = String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
        Method setterMethod = null;
        try {
            setterMethod = getMethod("set" + name, object.getClass());
            if (setterMethod == null) {
                field.set(object, value);
            } else {
                setterMethod.invoke(object, value);
            }
        } catch (Exception e) {
            LOGGER.warn("Cannot executing setter/getter method " + object.getClass().getSimpleName() + "." + name);
        }
        
        return value;
    }

   
    private static boolean isDateField(Field field) {
        return field.getName().contains("date") || field.getName().contains("Date") || field.getName().contains("Iso");
    }

    private static Method getMethod(String name, Class<?> type) {
        Method result = null;
        int number = 0;
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                result = method;
                number++;
            }
            if (number > 1) {
                //LOGGER.info("There are more than 1 method name " + type.getSimpleName() + "." + name + "..skip it");
            }
        }

        return result;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getObjectFromSpringProxy(Object proxy, Class<T> type) {
        Optional<Method> optional = Arrays.asList(proxy.getClass().getDeclaredMethods()).stream()
                .filter(method -> method.getName().endsWith("getTargetSource")).findFirst();
        if (optional.isPresent()) {
            Method method = optional.get();
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            try {
                TargetSource source = ((TargetSource)method.invoke(proxy));
                return (T) source.getTarget();
            } catch (Exception e) {
                LOGGER.error("Cannot get object {} from spring proxy", type.getSimpleName(), e);
            }
            method.setAccessible(accessible);
        }
        
        return null;
    }
}
