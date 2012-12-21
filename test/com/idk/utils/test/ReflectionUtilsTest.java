/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.exception.FieldNotFoundException;
import com.idk.object.ExtendedField;
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

    private One one = new One();

    /**
     * Test of getNestedProperty method, of class ReflectionUtils.
     */
    @Test
    public void testGetNestedProperty() throws Exception {
        Object result = (String) ReflectionUtils.getProperty(one, "threeString");
        assertEquals("threeValue", result.toString());
        result = (Collection<Two>) ReflectionUtils.getProperty(one, "twoList");
        assertEquals(one.getTwoList(), result);
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
        ReflectionUtils.setProperty(one, "threeString", "newValue");
        assertEquals(ReflectionUtils.getProperty(one, "threeString"), "newValue");
        ReflectionUtils.setProperty(one, "twoList", one.getTwoList());
        assertEquals(ReflectionUtils.getProperty(one, "twoList"), one.getTwoList());
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
     * Test of getAllClassFields method, of class ReflectionUtils.
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
     * Test of getAllFieldObjects method, of class ReflectionUtils.
     */
    @Test
    public void testGetFieldObjects() throws Exception {
        Collection<Field> fields = ReflectionUtils.getFieldObjects(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("two"));
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
        checkFields.add(One.class.getDeclaredField("two"));
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
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
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of getAllPrimitiveFields method, of class ReflectionUtils.
     */
    @Test
    public void testGetAllPrimitiveFields() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllPrimitiveFields(One.class);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("three")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of removeFieldObjects method, of class ReflectionUtils.
     */
    @Test
    public void testRemoveFieldObjects() throws Exception {
        Collection<Field> fields = ReflectionUtils.getAllFields(One.class);
        fields = ReflectionUtils.removeFieldObjects(fields);
        Collection<Field> checkFields = new ArrayList<Field>();
        checkFields.add(One.class.getDeclaredField("oneString"));
        checkFields.add(One.class.getDeclaredField("twoList"));
        checkFields.add(Two.class.getDeclaredField("twoString"));
        checkFields.add(Three.class.getDeclaredField("threeString"));
        checkFields.add(Base.class.getDeclaredField("baseString"));
        assertTrue(fields.containsAll(checkFields));
        assertTrue(!fields.contains(One.class.getDeclaredField("two")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("three")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseObject")));
    }

    /**
     * Test of removeFieldPrimitives method, of class ReflectionUtils.
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
        assertTrue(!fields.contains(One.class.getDeclaredField("twoList")));
        assertTrue(!fields.contains(Two.class.getDeclaredField("twoString")));
        assertTrue(!fields.contains(Three.class.getDeclaredField("threeString")));
        assertTrue(!fields.contains(Base.class.getDeclaredField("baseString")));
    }

    /**
     * Test of extendedGetAllClassFields method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedGetAllClassFields() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetAllClassFields(one);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("oneString");
        fieldNames.add("two");
        fieldNames.add("twoList");
        fieldNames.add("baseString");
        fieldNames.add("baseObject");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            assertTrue(field.getObject().equals(one));
        }
    }

    /**
     * Test of extendedGetFieldObjects method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedGetFieldObjects() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetFieldObjects(one);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("two");
        fieldNames.add("baseObject");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            assertTrue(field.getObject().equals(one));
        }
    }

    /**
     * Test of extendedGetAllFields method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedGetAllFields() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetAllFields(one);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("oneString");
        fieldNames.add("twoString");
        fieldNames.add("threeString");
        fieldNames.add("baseString");
        fieldNames.add("two");
        fieldNames.add("twoList");
        fieldNames.add("three");
        fieldNames.add("baseObject");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            if ("oneString".equals(field.getField().getName()) || "baseString".equals(field.getField().getName()) || "two".equals(field.getField().getName()) || "twoList".equals(field.getField().getName()) || "baseObject".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one));
            } else if ("twoString".equals(field.getField().getName()) || "three".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo()));
            } else if ("three".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo().getThree()));
            }
        }
    }

    /**
     * Test of extendedGetPrimitiveFields method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedGetPrimitiveFields() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetPrimitiveFields(one);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("oneString");
        fieldNames.add("twoList");
        fieldNames.add("baseString");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            assertTrue(field.getObject().equals(one));
        }
    }

    /**
     * Test of extendedGetAllPrimitiveFields method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedGetAllPrimitiveFields() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetAllPrimitiveFields(one);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("oneString");
        fieldNames.add("twoString");
        fieldNames.add("threeString");
        fieldNames.add("baseString");
        fieldNames.add("twoList");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            if ("oneString".equals(field.getField().getName()) || "baseString".equals(field.getField().getName()) || "twoList".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one));
            } else if ("twoString".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo()));
            } else if ("threeString".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo().getThree()));
            }
        }
    }

    /**
     * Test of extendedRemoveFieldObjects method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedRemoveFieldObjects() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetAllFields(one);
        fields = ReflectionUtils.extendedRemoveFieldObjects(fields);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("oneString");
        fieldNames.add("twoString");
        fieldNames.add("threeString");
        fieldNames.add("baseString");
        fieldNames.add("twoList");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            if ("oneString".equals(field.getField().getName()) || "baseString".equals(field.getField().getName()) || "twoList".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one));
            } else if ("twoString".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo()));
            } else if ("threeString".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one.getTwo().getThree()));
            }
        }
    }

    /**
     * Test of extendedRemoveFieldPrimitives method, of class ReflectionUtils.
     */
    @Test
    public void testExtendedRemoveFieldPrimitives() throws Exception {
        Collection<ExtendedField> fields = ReflectionUtils.extendedGetAllFields(one);
        fields = ReflectionUtils.extendedRemoveFieldPrimitives(fields);
        Collection<String> fieldNames = new ArrayList();
        fieldNames.add("two");
        fieldNames.add("three");
        fieldNames.add("baseObject");
        assertTrue(fields.size() == fieldNames.size());
        for (ExtendedField field : fields) {
            assertTrue(fieldNames.contains(field.getField().getName()));
            if ("two".equals(field.getField().getName()) || "baseObject".equals(field.getField().getName())) {
                assertTrue(field.getObject().equals(one));
            } else {
                assertTrue(field.getObject().equals(one.getTwo()));
            }
        }
    }
}
