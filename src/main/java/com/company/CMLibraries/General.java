package com.company.CMLibraries;

public class General {

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
