/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.utils.FormatUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author shoemaki
 */
public class FormatUtilsTest {

    public FormatUtilsTest() {
    }

    /**
     * Test of formatUpperToUnderscore method, of class FormatUtils.
     */
    @Test
    public void testFormatUpperToUnderscore() {
        System.out.println("formatUpperToUnderscore");
        String unformattedName = "thisIsAStringWithCapitals";
        String expResult = "this_is_a_string_with_capitals";
        String result = FormatUtils.formatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);
    }

    /**
     * Test of formatUnderscoreToUpper method, of class FormatUtils.
     */
    @Test
    public void testFormatUnderscoreToUpper() {
        System.out.println("testUnFormatUnderscoreToUpper");
        String formattedName = "this_is_a_string_with_underscores";
        String expResult = "thisIsAStringWithUnderscores";
        String result = FormatUtils.formatUnderscoreToUpper(formattedName);
        assertEquals(expResult, result);
    }

    /**
     * Test of addColumnsAsString method, of class FormatUtils.
     */
//    @Test
//    public void testAddColumnsAsString() {
//        System.out.println("addColumnsAsString");
//        Collection<Object> items = new ArrayList<Object>();
//        items.add("stringOne");
//        items.add("stringTwo");
//        String expResult = "(stringOne, stringTwo)";
//        String result = FormatUtils.addColumnsAsString(items);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of addValuesAsString method, of class FormatUtils.
     */
//    @Test
//    public void testAddValuesAsString() {
//        System.out.println("addValuesAsString");
//        Collection<Object> items = new ArrayList<Object>();
//        items.add("stringOne");
//        items.add("stringTwo");
//        String expResult = "('stringOne', 'stringTwo')";
//        String result = FormatUtils.addValuesAsString(items);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of lowerFirstCapital method, of class FormatUtils.
     */
    @Test
    public void testLowerFirstCapital() {
        System.out.println("lowerFirstCapital");
        String stringToLower = "thisIsAString";
        String expResult = "thisisAString";
        String result = FormatUtils.lowerFirstCapital(stringToLower);
        assertEquals(expResult, result);
    }
}
