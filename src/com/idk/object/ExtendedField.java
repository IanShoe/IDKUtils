/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.object;

import java.lang.reflect.Field;

/**
 * Wrapper class used to hold the object that a field came from.
 *
 * @author shoemaki
 */
public class ExtendedField {

    public ExtendedField(Field field, Object object) {
        this.field = field;
        this.object = object;
    }
    private Field field;
    private Object object;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
