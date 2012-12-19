/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.object;

/**
 *
 * @author shoemaki
 */
public class Two {

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