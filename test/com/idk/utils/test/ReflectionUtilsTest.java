/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.utils.ReflectionUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author shoemaki
 */
public class ReflectionUtilsTest {

    public static class One {

        private String oneString = "oneValue";
        private Two two = new Two();

        public Two getTwo() {
            return this.two;
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
        System.out.println("Testing getNestedProperty");
        One one = new One();
        Object result = ReflectionUtils.getProperty(one, "threeString");
        assertEquals("threeValue", result.toString());
        result = ReflectionUtils.getProperty(one, "twoString");
        assertEquals("twoValue", result.toString());
        assertNull(ReflectionUtils.getProperty(one, "doesNotExist"));
    }
}
