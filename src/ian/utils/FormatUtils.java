/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ian.utils;

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
