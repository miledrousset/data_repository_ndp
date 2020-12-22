package com.cnrs.ndp.utils;

public class StringUtils {

    
    
    public static String formatFileName(String name) {
        String newString = name;
        newString = newString.replace(' ', '_');
        newString = StringUtils.removeSpecialCharacter(name);
        return newString;
    }

    
    public static String removeSpecialCharacter(String str) {

        String resultStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)=='_' || str.charAt(i)=='.' || Character.isDigit(str.charAt(i)) ||
                    str.charAt(i) > 64 && str.charAt(i) <= 122) {
                resultStr = resultStr + str.charAt(i);
            }
        }
        return resultStr;
    }
}
