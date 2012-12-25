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
        String unformattedName = "thisIsAStringWithCapitals";
        String expResult = "this_is_a_string_with_capitals";
        String result = FormatUtils.formatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);
    }

    /**
     * Test of formatUpperToUnderscore method, of class FormatUtils.
     */
    @Test
    public void testFormatSmartAllUpperToUnderscore() {
        String unformattedName = "thisIsAString";
        String expResult = "this_is_a_string";
        String result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ThisIsAString";
        expResult = "this_is_a_string";
        result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ROYGBIV";
        expResult = "roygbiv";
        result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "MVDConfig";
        expResult = "mvd_config";
        result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ACRONymACRO";
        expResult = "acro_nym_acro";
        result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "TestEVERYThingMAN";
        expResult = "test_every_thing_man";
        result = FormatUtils.formatSmartAllUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);
    }

    /**
     * Test of formatUnderscoreToUpper method, of class FormatUtils.
     */
    @Test
    public void testFormatUnderscoreToUpper() {
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
        String stringToLower = "thisIsAString";
        String expResult = "thisisAString";
        String result = FormatUtils.lowerFirstCapital(stringToLower);
        assertEquals(expResult, result);
    }
}
