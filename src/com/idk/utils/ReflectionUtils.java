/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

import com.idk.exception.FieldNotFoundException;
import com.idk.exception.FieldSetException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author shoemaki
 */
public class ReflectionUtils {

    /**
     * This is a list of all the primitive wrapper classes I want to ignore when
     * searching for a class' nested complex objects.
     */
    private static final HashSet<Class<?>> ignoreList = setIgnoreList();

    /**
     * Method to set the ignore list for primitive wrappers
     *
     * @return
     */
    private static HashSet<Class<?>> setIgnoreList() {
        HashSet<Class<?>> classIgnoreList = new HashSet<Class<?>>();
        classIgnoreList.add(Boolean.class);
        classIgnoreList.add(Byte.class);
        classIgnoreList.add(Character.class);
        classIgnoreList.add(Double.class);
        classIgnoreList.add(Float.class);
        classIgnoreList.add(Integer.class);
//        classIgnoreList.add(List.class); // somehow I will implement this later
//        classIgnoreList.add(Collection.class); // somehow I will implement this later
        classIgnoreList.add(Long.class);
        classIgnoreList.add(String.class);
        classIgnoreList.add(Short.class);
        classIgnoreList.add(Void.class);
        return classIgnoreList;
    }

    /**
     * Attempt to getProperty in base class first. If not found, search
     * recursively for nested class properties
     *
     * @param object baseObject
     * @param fieldString field to identify
     * @return identified field's value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object getProperty(Object object, String fieldString) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, FieldNotFoundException {
        try {
            return PropertyUtils.getProperty(object, fieldString);
        } catch (Exception ex) {
            //Try finding nested
            String nestedString = ReflectionUtils.findNestedField(object.getClass(), fieldString);
            if (nestedString != null) {
                return PropertyUtils.getNestedProperty(object, nestedString);
            } else {
                throw new FieldNotFoundException("Could not find field " + fieldString + " for object " + object.getClass().getSimpleName());
            }
        }
    }

    /**
     * Attempt to setProperty in base class first. If not found, search
     * recursively for nested class properties.
     *
     * @param object baseObject
     * @param fieldString field to identify
     * @param value value to set
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void setProperty(Object object, String fieldString, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, FieldSetException {
        try {
            PropertyUtils.setProperty(object, fieldString, value);
        } catch (Exception ex) {
            //Try finding nested
            String nestedString = ReflectionUtils.findNestedField(object.getClass(), fieldString);
            if (nestedString != null) {
                PropertyUtils.setNestedProperty(object, nestedString, value);
            } else {
                throw new FieldSetException("Could not set field " + fieldString + " for object " + object.getClass().getSimpleName() + " with value " + value);
            }
        }
    }

    /**
     * The meat and potatoes method to find a nested field any amount of layers
     * deep. The result is the string path to the nested field for propertyUtils
     * Example: "Person.address.city" where person & address are complex
     * objects.
     *
     * @param baseClass
     * @param fieldString
     * @return string version for PropertyUtils to getNestedProperty with
     */
    private static String findNestedField(Class<?> baseClass, String fieldString) {
        Collection<Field> currentObjectFields = findFieldObjects(baseClass);
        Collection<Field> possibleFields = new ArrayList<Field>();
        for (Field field : currentObjectFields) {
            Class<?> clazz = field.getType();
            try {
                clazz.getDeclaredField(fieldString);
                return field.getName() + "." + fieldString;
            } catch (NoSuchFieldException e) {
                possibleFields.add(field);
            }
        }
        for (Field field : possibleFields) {
            String possible = findNestedField(field.getType(), fieldString);
            if (possible != null) {
                StringBuilder fieldBuilder = new StringBuilder(field.getName() + ".");
                return fieldBuilder.append(possible).toString();
            }
        }
        return null;
    }

    /**
     * Method to determine which fields in a class are objects instead of
     * "primitive" types
     *
     * @param clazz class to search
     * @return list of fields that are complex classes
     */
    private static Collection<Field> findFieldObjects(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.getType().isPrimitive() && !ignoreList.contains(field.getType())) {
                // Found nested object under baseObject
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Helper method to get a class's fields found locally and in it's parent
     * classes
     *
     * @param type class to find all fields
     * @return list of fields
     */
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
