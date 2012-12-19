/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.object;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author shoemaki
 */
public class One extends Base {

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
