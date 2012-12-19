/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.object;

/**
 *
 * @author shoemaki
 */
public abstract class Base {

    private String baseString = "baseValue";
    private Object baseObject = new Object();

    public String getBaseString() {
        return baseString;
    }

    public void setBaseString(String baseString) {
        this.baseString = baseString;
    }

    public Object getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(Object baseObject) {
        this.baseObject = baseObject;
    }
}