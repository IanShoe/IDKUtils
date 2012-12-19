/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.exception.FieldNotFoundException;
import com.idk.utils.ReflectionUtils;
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

    public static abstract class testBase {

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

    public static class One extends testBase {

        private String oneString = "oneValue";
        private Two two = new Two();
        private Collection<Two> twoList = new ArrayList<Two>();

        public Collection<Two> getTwoList() {
            return twoList;
        }

        public void setTwoList(Collection<Two> twoList) {
            this.twoList = twoList;
        }

        public Two getTwo() {
            return this.two;
        }

        public void setTwo(Two two) {
            this.two = two;
        }

        public String getOneString() {
            return this.oneString;
        }

        public void setOneString(String oneString) {
            this.oneString = oneString;
        }
    }

    public static class Two {

        private String twoString = "twoValue";
        private Three three = new Three();

        public Three getThree() {
            return this.three;
        }

        public void setThree(Three three) {
            this.three = three;
        }

        public String getTwoString() {
            return this.twoString;
        }

        public void setTwoString(String twoString) {
            this.twoString = twoString;
        }
    }

    public static class Three {

        private String threeString = "threeValue";

        public String getThreeString() {
            return this.threeString;
        }

        public void setThreeString(String threeString) {
            this.threeString = threeString;
        }
    }

    public ReflectionUtilsTest() {
    }

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
        checkFields.add(testBase.class.getDeclaredField("baseString"));
        checkFields.add(testBase.class.getDeclaredField("baseObject"));
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
        checkFields.add(testBase.class.getDeclaredField("baseObject"));
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
        checkFields.add(testBase.class.getDeclaredField("baseString"));
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(Two.class.getDeclaredField("three"));
        checkFields.add(testBase.class.getDeclaredField("baseObject"));
        assertTrue(fields.containsAll(checkFields));
    }
}