/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

import com.idk.exception.FieldNotFoundException;
import com.idk.exception.FieldSetException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
     * deep. Right now I also try to ensure that the result follows Java Naming
     * Conventions. Class names have their first case capitalized but their
     * instances do not. We want the instance so I lower the first capital.
     *
     * The result is the string path to the nested field for propertyUtils
     * Example: "Person.address.city" where person & address is a complex object
     *
     * @param baseClass
     * @param fieldString
     * @return string version for PropertyUtils to getNestedProperty with
     */
    private static String findNestedField(Class baseClass, String fieldString) {
        Collection<Class> currentObjectClasses = findClassObjects(baseClass);

        //list of nested classes that did not contain the field but can be checked deeper
        Collection<Class> possibleClasses = new ArrayList<Class>();

        for (Class clazz : currentObjectClasses) {
            try {
                clazz.getDeclaredField(fieldString);
                return FormatUtils.lowerFirstCapital(clazz.getSimpleName()) + "." + fieldString;
            } catch (NoSuchFieldException ex) {
                possibleClasses.add(clazz);
            }
        }
        for (Class clazz : possibleClasses) {
            String possible = findNestedField(clazz, fieldString);
            if (possible != null) {
                StringBuilder fieldBuilder = new StringBuilder(FormatUtils.lowerFirstCapital(clazz.getSimpleName()) + ".");
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
     * @return list of complex classes
     */
    private static Collection<Class> findClassObjects(Class clazz) {
        Collection<Class> objects = new ArrayList<Class>();
        for (Field field : clazz.getDeclaredFields()) {
            Class fieldClass = field.getType();
            if (!fieldClass.isPrimitive() && !ignoreList.contains(fieldClass)) {
                //Found nested object under baseObject
                objects.add(field.getType());
            }
        }
        return objects;
    }

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
}
