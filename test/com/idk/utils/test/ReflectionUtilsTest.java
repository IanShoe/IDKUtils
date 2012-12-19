/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.exception.FieldNotFoundException;
import com.idk.utils.ReflectionUtils;
import com.idk.utils.object.Base;
import com.idk.utils.object.One;
import com.idk.utils.object.Three;
import com.idk.utils.object.Two;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author shoemaki
 */
public class ReflectionUtilsTest {

    /**
     * Test of getNestedProperty method, of class ReflectionUtils.
     */
    @Test
    public void testGetNestedProperty() throws Exception {
        One one = new One();
        Collection<Two> twoz = new ArrayList<Two>();
        Two two = new Two();
        twoz.add(two);
        one.setTwoList(twoz);
        Object result = (String) ReflectionUtils.getProperty(one, "threeString");
        assertEquals("threeValue", result.toString());
        result = (Collection<Two>) ReflectionUtils.getProperty(one, "twoList");
        assertEquals(twoz, result);
        try {
            result = ReflectionUtils.getProperty(one, "doesNotExist");
            fail("FieldNotFoundExcpetion was not thrown when it should have been. Unintended result: " + result);
        } catch (FieldNotFoundException ex) {
        }
    }

    /**
     * Test of setNestedProperty method, of class ReflectionUtils.
     */
    @Test
    public void testSetNestedProperty() throws Exception {
        One one = new One();
        Collection<Two> twoz = new ArrayList<Two>();
        Two two = new Two();
        twoz.add(two);
        one.setTwoList(twoz);
        ReflectionUtils.setProperty(one, "threeString", "newValue");
        assertEquals(ReflectionUtils.getProperty(one, "threeString"), "newValue");
        ReflectionUtils.setProperty(one, "twoList", twoz);
        assertEquals(ReflectionUtils.getProperty(one, "twoList"), twoz);
        try {
            ReflectionUtils.setProperty(one, "doesNotExist", new Object());
            fail("FieldNotFoundException was not thrown when it should have been.");
        } catch (FieldNotFoundException ex) {
        }
        try {
            ReflectionUtils.setProperty(one, "twoList", "wrongType");
            fail("IllegalArguementException was not thrown when it should have been.");
        } catch (IllegalArgumentException ex) {
        }
    }

    /**
     * Test of getAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetAllClassFields() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllClassFields(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        checkFields.add(Base.class.getDeclaredField("baseObject"));
        assertTrue(fields.containsAll(checkFields));
    }

    /**
     * Test of getAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetFieldObjects() throws Exception {
        Collection<Field> fields = ReflectionUtils.getFieldObjects(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Base.class.getDeclaredField("baseObject"));
        assertTrue(fields.containsAll(checkFields));
    }

    /**
     * Test of getAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetAllFields() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllFields(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(Two.class.getDeclaredField("three"));
        checkFields.add(Base.class.getDeclaredField("baseObject"));
        assertTrue(fields.containsAll(checkFields));
    }

    /**
     * Test of getPrimitiveFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetPrimitiveFields() throws Exception {
        Collection<Field> fields = ReflectionUtils.getPrimitiveFields(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(One.class.getDeclaredField("twoList")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of getAllPrimitiveFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetAllPrimitiveFields() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllFields(One.class);
        fields = ReflectionUtils.removeFieldObjects(fields);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("three")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of getAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testRemoveFieldObjects() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllFields(One.class);
        fields = ReflectionUtils.removeFieldObjects(fields);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("three")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of getAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testRemoveFieldPrimitives() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllFields(One.class);
        fields = ReflectionUtils.removeFieldPrimitives(fields);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(Two.class.getDeclaredField("three"));
        checkFields.add(Base.class.getDeclaredField("baseObject"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("oneString")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("twoString")));
        assertTrue(!fields.contains(Three.class.getDeclaredField("threeString")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseString")));
    }
}