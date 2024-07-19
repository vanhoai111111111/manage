package com.example.manage.Model;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }
}
