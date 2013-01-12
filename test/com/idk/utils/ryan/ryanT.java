/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.ryan;

import com.idk.ryan.RyanFormatUtils;
import com.idk.utils.FormatUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Ryan
 */
public class ryanT {
      @Test
    public void testFormatSmartAllUpperToUnderscore() {
        String unformattedName = "thisIsAString";
        String expResult = "this_is_a_string";
        String result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ThisIsAString";
        expResult = "this_is_a_string";
        result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ROYGBIV";
        expResult = "roygbiv";
        result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "MVDConfig";
        expResult = "mvd_config";
        result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "ACRONymACRO";
        expResult = "acro_nym_acro";
        result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);

        unformattedName = "TestEVERYThingMAN";
        expResult = "test_every_thing_man";
        result = RyanFormatUtils.ryanFormatUpperToUnderscore(unformattedName);
        assertEquals(expResult, result);
    }
}
