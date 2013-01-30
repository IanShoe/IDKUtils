/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.ryan;

/**
 * This is an awesome util that Ryan has created to mimic Ian's format utility!
 * Do it well!
 * @author Ryan
 */
public class RyanFormatUtils {
    public static String ryanFormatUpperToUnderscore(String varOne){
        
        String formattedVarOne;
        char [] tempStringArray = varOne.toCharArray();
        int x = 0;
        int y = 0;
        
        void String stringTraverse() {
                        
            if (tempStringArray[x] islower) {
                y = x;
                while (tempStringArray[x] islower) {
                    x++
                }
                if (formattedVarOne) {
                    formattedVarOne = formattedVarOne + "_";
                    for (y ; y < x; y++) {
                        formattedVarOne = formattedVarOne + tempStringArray[y];
                    }
                }
                else {
                    for (y ; y < x; y++) {
                        formattedVarOne = formattedVarOne + tempStringArray[y];
                    }
                }
                stringTraverse();
            }
            
            else if (tempStringArray[x] isupper) {
                y = x;
                int counter = 0;
                
                while (tempStringArray[x] isupper) {
                    x++;
                    counter++;
                }
                if (counter > 1) {
                    if (formattedVarOne) {
                        formattedVarOne = formattedVarOne + "_";
                        for (y ; y < x; y++) {
                            formattedVarOne = formattedVarOne + tempStringArray[y];
                        }
                    }
                }
            }
            
            
        return formattedVarOne;
        }
    }
}




/*Pseudocode for program design
 * set string main
 *      make array out of string main
 * create result string, set to empty
 * primary function
 *      create two markers
 * nest function one (if)
 *      if [x] in string main is lower
 *       z = x
 *       while x is lower
 *              x++
 *       add z-x to result string as a word
 *       recursion to primary function
 * nest function two (else if)
 *      else if [x] in string main is upper
 *          create counter and set to 0
 *          z = x
 *          while x is upper
 *              x++
 *              counter++
 *          if counter > 1
 *              add z-x as acronym (or single letter word) to result string
 *              recursion to primary function
 *          else
 *              while x is lower
 *                  x++
 *              add z-x to result string as word
 *              recursion to primary function
 * no nest function applicable (else)
 *      end of string
 *      return result string
 * 
 */