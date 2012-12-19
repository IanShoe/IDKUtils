/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

import com.idk.exception.FieldNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

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
        classIgnoreList.add(Long.class);
        classIgnoreList.add(String.class);
        classIgnoreList.add(Short.class);
        classIgnoreList.add(Void.class);
        return classIgnoreList;
    }

    /**
     * Attempt to getProperty from an object and it's underlying objects.
     *
     * @param object baseObject
     * @param fieldString field to identify
     */
    public static Object getProperty(Object object, String fieldString) throws IllegalArgumentException, IllegalAccessException, FieldNotFoundException {
        Collection<Field> fields = getAllClassFields(object.getClass());
        for (Field field : fields) {
            if (field.getName().equals(fieldString)) {
                field.setAccessible(true);
                return field.get(object);
            }
        }
        fields = getFieldObjects(object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                return getProperty(field.get(object), fieldString);
            } catch (FieldNotFoundException ex) {
                //ignore in recursion
            }
        }
        throw new FieldNotFoundException("Could not find field with name :" + fieldString + ".");
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
    public static void setProperty(Object object, String fieldString, Object value) throws IllegalArgumentException, IllegalAccessException, FieldNotFoundException {
        Collection<Field> fields = getAllClassFields(object.getClass());
        for (Field field : fields) {
            if (field.getName().equals(fieldString)) {
                field.setAccessible(true);
                field.set(object, value);
                return;
            }
        }
        fields = getFieldObjects(object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                setProperty(field.get(object), fieldString, value);
                return;
            } catch (FieldNotFoundException ex) {
                //ignore in recursion
            }
        }
        throw new FieldNotFoundException("Could not find field with name :" + fieldString + ".");
    }

    /**
     * Method to get a class's fields found locally and in it's parent
     * classes
     *
     * @param type class to find all fields
     * @return list of fields
     */
    public static Collection<Field> getAllClassFields(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<Field>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Method to determine which fields in a class are objects instead of
     * "primitive" types
     *
     * @param clazz class to search
     * @return list of fields that are complex classes
     */
    public static Collection<Field> getFieldObjects(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<Field>();
        for (Field field : getAllClassFields(clazz)) {
            if (!field.getType().isPrimitive() && !ignoreList.contains(field.getType())) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Method to get all fields in an object including parent and containing
     * objects.
     *
     * @param clazz base class
     * @return complete list of fields
     */
    public static Collection<Field> getAllFields(Class<?> clazz) {
        Collection<Field> fields = getAllClassFields(clazz);
        Collection<Field> fieldObjects = getFieldObjects(clazz);
        for (Field field : fieldObjects) {
            fields.addAll(getAllFields(field.getType()));
        }
        return fields;
    }
}