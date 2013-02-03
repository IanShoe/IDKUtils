/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.ryan;

/**
 * This is an awesome util that Ryan has created to mimic Ian's format utility!
 * Do it well!
 *
 * @author Ryan
 */
public class RyanFormatUtils {

    public static String ryanFormatUpperToUnderscore(String varOne) {

        //Ian just a temporary null assignment. Remove when ready to use
        String formattedVarOne = null;
        char[] tempStringArray = varOne.toCharArray();
        int x = 0;
        int y = 0;
        return formattedVarOne;
    }
    
    // This is what you had, take a look at how it should look.
    // void String stringTraverse() {
    public static String stringTraverse() {
                        
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
}


// Dear Ryan,
// Not a bad start. I like your first sections where you check if the current
// character is upper or lower and then keep a while loop to check subsequent
// characters if they are the same. I believe though, your only going to want to
// check if a letter is upper though. If lower, then you simply want to 
// increment your main counter variable that traverses the UNformatted string.

// Second, the reason why your second method was squiggled red is because you're
// putting a method within a method. Remember with Object Oriented Programing,
// your class contains various methods which you can call within the same class.
// I've fixed up that part so you'll need to make sure you link up the main
// with your second method. (2nd method needs things passed to it in the params)

// GOOD LUCK!

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