/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.ian;

import com.idk.utils.FormatUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 * @author Ian
 */
public class ianTest {
 
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
}
