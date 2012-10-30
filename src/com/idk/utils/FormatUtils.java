/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

/**
 *
 * @author shoemaki
 */
public class FormatUtils {

    /**
     * Takes a camel case string and changes it to lowercase and underscore
     *
     * Example: thisIsAString --> this_is_a_string
     *
     * @param unformattedName string to format
     * @return newly formatted string
     */
    public static String formatUpperToUnderscore(String unformattedName) {
        String formattedName = unformattedName;
        char[] chars = unformattedName.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                StringBuilder modifier = new StringBuilder(formattedName);
                modifier.setCharAt(i + offset, Character.toLowerCase(formattedName.charAt(i + offset)));
                formattedName = modifier.insert(i + offset, '_').toString();
                offset++;
            }
        }
        return formattedName;
    }

    /**
     * Takes a string and attempts to change all UpperCase letters into
     * LowerCase and delimited by underscore. The method will try to guess if
     * there is an acronym in the name by assuming that acronyms are capitalized
     * and have at least 2 consecutive uppercase characters
     *
     * @param unformattedName string to format
     * @return newly formatted string
     */
    public static String formatSmartAllUpperToUnderscore(String unformattedName) {
        StringBuilder modifier = new StringBuilder(unformattedName);
        char[] chars = unformattedName.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                if (i == 0) {
                    int lowerNumber = determineAcronym(chars, i);
                    int j = i;
                    for (; j < i + lowerNumber; j++) {
                        modifier.setCharAt(j, Character.toLowerCase(unformattedName.charAt(j)));
                    }
                    i = j - 1;
                } else {
                    int lowerNumber = determineAcronym(chars, i);
                    int j = i;
                    for (; j < i + lowerNumber; j++) {
                        modifier.setCharAt(j + offset, Character.toLowerCase(unformattedName.charAt(j)));
                    }
                    modifier.insert(i + offset, '_');
                    offset++;
                    i = j - 1;
                }
            }
        }
        return modifier.toString();
    }

    /**
     * Helper Method to determine an acronym in a char array given a current
     * capital letter
     *
     * @param chars chars to check
     * @param startPos starting capital letter
     * @return number of chars to lower
     */
    private static int determineAcronym(char[] chars, int startPos) {
        int len = chars.length;
        if (startPos + 1 < len && Character.isUpperCase(chars[startPos + 1])) {
            if (startPos + 2 < len && Character.isUpperCase(chars[startPos + 2])) {
                int lowerNumber = 2;
                int currentPos = startPos + 3;
                try {
                    char ch = chars[currentPos];
                    while (currentPos < len && Character.isUpperCase(ch)) {
                        lowerNumber++;
                        currentPos++;
                        ch = chars[currentPos];
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    return lowerNumber + 1;
                }
                return lowerNumber;
            } else {
                return 1;
            }
        }
        return 1;
    }

    /**
     * Takes a lowercase and underscore string and changes it to camel case
     *
     * Example: this_is_a_string --> thisIsAString
     *
     * @param formattedName
     * @return newly formatted string
     */
    public static String formatUnderscoreToUpper(String unformattedName) {
        String formattedName = unformattedName;
        char[] chars = unformattedName.toCharArray();
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '_') {
                StringBuilder modifier = new StringBuilder(formattedName);
                modifier.deleteCharAt(i - offset).setCharAt(i - offset, Character.toUpperCase(modifier.charAt(i - offset)));
                formattedName = modifier.toString();
                offset++;
            }
        }
        return formattedName;
    }

    /**
     * Lowers the first capital character in a string
     *
     * @param stringToLower
     * @return
     */
    public static String lowerFirstCapital(String stringToLower) {
        char[] chars = stringToLower.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                StringBuilder sb = new StringBuilder(stringToLower);
                sb.setCharAt(i, Character.toLowerCase(chars[i]));
                return sb.toString();
            }
        }
        return stringToLower;
    }
}
