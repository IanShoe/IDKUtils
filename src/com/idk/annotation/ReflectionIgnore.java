/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for reflection to ignore fields
 *
 * @author shoemaki
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectionIgnore {
}