/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

import com.idk.exception.FieldNotFoundException;
import com.idk.object.ExtendedField;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
        classIgnoreList.add(Collection.class);
        classIgnoreList.add(Double.class);
        classIgnoreList.add(Float.class);
        classIgnoreList.add(Integer.class);
        classIgnoreList.add(List.class);
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
     * @return list of fields that are objects
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

    /**
     * Method to determine which fields in a class are "primitive" types instead
     * of objects
     *
     * @param clazz class to search
     * @return list of fields that are primitives
     */
    public static Collection<Field> getPrimitiveFields(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<Field>();
        for (Field field : getAllClassFields(clazz)) {
            if (field.getType().isPrimitive() || ignoreList.contains(field.getType())) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Method to get all primitive fields in an object including parent and
     * containing objects.
     *
     * @param clazz base class
     * @return list of primitive fields
     */
    public static Collection<Field> getAllPrimitiveFields(Class<?> clazz) {
        Collection<Field> fields = getPrimitiveFields(clazz);
        Collection<Field> fieldObjects = getFieldObjects(clazz);
        for (Field field : fieldObjects) {
            fields.addAll(getAllPrimitiveFields(field.getType()));
        }
        return fields;
    }

    /**
     * Method to remove objects from a list of fields
     *
     * @param list of fields to remove from
     * @return list without objects
     */
    public static Collection<Field> removeFieldObjects(Collection<Field> fields) {
        fields = new CopyOnWriteArrayList<Field>(fields);
        for (Field field : fields) {
            if (!field.getType().isPrimitive() && !ignoreList.contains(field.getType())) {
                fields.remove(field);
            }
        }
        return fields;
    }

    /**
     * Method to remove primitives from a list of fields
     *
     * @param list of fields to remove from
     * @return list without primitives
     */
    public static Collection<Field> removeFieldPrimitives(Collection<Field> fields) {
        fields = new CopyOnWriteArrayList<Field>(fields);
        for (Field field : fields) {
            if (field.getType().isPrimitive() || ignoreList.contains(field.getType())) {
                fields.remove(field);
            }
        }
        return fields;
    }

    /**
     * Method to get a class's extendedFields found locally and in it's parent
     * classes
     *
     * @param type class to find all extendedFields
     * @return list of extendedFields
     */
    public static Collection<ExtendedField> extendedGetAllClassFields(Object baseObject) {
        Class clazz = baseObject.getClass();
        Collection<ExtendedField> extendedFields = new ArrayList<ExtendedField>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                ExtendedField extendedField = new ExtendedField(field, baseObject);
                extendedFields.add(extendedField);
            }
        }
        return extendedFields;
    }

    /**
     * Method to determine which extendedFields in a class are objects instead
     * of
     * "primitive" types
     *
     * @param clazz class to search
     * @return list of extendedFields that are objects
     */
    public static Collection<ExtendedField> extendedGetFieldObjects(Object baseObject) {
        Collection<ExtendedField> extendedFields = new ArrayList<ExtendedField>();
        for (ExtendedField extendedField : extendedGetAllClassFields(baseObject)) {
            if (!extendedField.getField().getType().isPrimitive() && !ignoreList.contains(extendedField.getField().getType())) {
                extendedFields.add(extendedField);
            }
        }
        return extendedFields;
    }

    /**
     * Method to get all extendedFields in an object including parent and
     * containing
     * objects.
     *
     * @param clazz base class
     * @return complete list of extendedFields
     */
    public static Collection<ExtendedField> extendedGetAllFields(Object baseObject) throws IllegalArgumentException, IllegalAccessException {
        Collection<ExtendedField> extendedFields = extendedGetAllClassFields(baseObject);
        Collection<ExtendedField> fieldObjects = extendedGetFieldObjects(baseObject);
        for (ExtendedField extendedField : fieldObjects) {
            Field field = extendedField.getField();
            field.setAccessible(true);
            extendedFields.addAll(extendedGetAllFields(field.get(baseObject)));
        }
        return extendedFields;
    }

    /**
     * Method to determine which extendedFields in a class are "primitive" types
     * instead
     * of objects
     *
     * @param clazz class to search
     * @return list of extendedFields that are primitives
     */
    public static Collection<ExtendedField> extendedGetPrimitiveFields(Object baseObject) {
        Collection<ExtendedField> extendedFields = new ArrayList<ExtendedField>();
        for (ExtendedField extendedField : extendedGetAllClassFields(baseObject)) {
            if (extendedField.getField().getType().isPrimitive() || ignoreList.contains(extendedField.getField().getType())) {
                extendedFields.add(extendedField);
            }
        }
        return extendedFields;
    }

    /**
     * Method to get all primitive extendedFields in an object including parent
     * and
     * containing objects.
     *
     * @param clazz base class
     * @return list of primitive extendedFields
     */
    public static Collection<ExtendedField> extendedGetAllPrimitiveFields(Object baseObject) throws IllegalArgumentException, IllegalAccessException {
        Collection<ExtendedField> extendedFields = extendedGetPrimitiveFields(baseObject);
        Collection<ExtendedField> fieldObjects = extendedGetFieldObjects(baseObject);
        for (ExtendedField extendedField : fieldObjects) {
            Field field = extendedField.getField();
            field.setAccessible(true);
            field.get(baseObject);
            extendedFields.addAll(extendedGetAllPrimitiveFields(field.get(baseObject)));
        }
        return extendedFields;
    }

    /**
     * Method to remove objects from a list of extendedFields
     *
     * @param list of extendedFields to remove from
     * @return list without objects
     */
    public static Collection<ExtendedField> extendedRemoveFieldObjects(Collection<ExtendedField> extendedFields) {
        extendedFields = new CopyOnWriteArrayList<ExtendedField>(extendedFields);
        for (ExtendedField extendedField : extendedFields) {
            if (!extendedField.getField().getType().isPrimitive() && !ignoreList.contains(extendedField.getField().getType())) {
                extendedFields.remove(extendedField);
            }
        }
        return extendedFields;
    }

    /**
     * Method to remove primitives from a list of extendedFields
     *
     * @param list of extendedFields to remove from
     * @return list without primitives
     */
    public static Collection<ExtendedField> extendedRemoveFieldPrimitives(Collection<ExtendedField> extendedFields) {
        extendedFields = new CopyOnWriteArrayList<ExtendedField>(extendedFields);
        for (ExtendedField extendedField : extendedFields) {
            if (extendedField.getField().getType().isPrimitive() || ignoreList.contains(extendedField.getField().getType())) {
                extendedFields.remove(extendedField);
            }
        }
        return extendedFields;
    }

    /**
     * Method to retrieve list of fields from extendedField wrapper
     *
     * @param extendedFields extended field collection
     * @return list of fields
     */
    public static Collection<Field> extendedRetrieveFieldCollection(Collection<ExtendedField> extendedFields) {
        Collection<Field> fields = new ArrayList<Field>();
        for (ExtendedField extendedField : extendedFields) {
            fields.add(extendedField.getField());
        }
        return fields;
    }

    /**
     * Method to retrieve list of objects from extendedField wrapper
     *
     * @param extendedFields extended field collection
     * @return list of objects
     */
    public static Collection<Object> extendedRetrieveObjectCollection(Collection<ExtendedField> extendedFields) {
        Collection<Object> objects = new ArrayList<Object>();
        for (ExtendedField extendedField : extendedFields) {
            objects.add(extendedField.getObject());
        }
        return objects;
    }
}