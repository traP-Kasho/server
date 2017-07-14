package com.github.traP_kasho.server;

import java.util.ResourceBundle;


public final class PropertyManager {
    private static final String PROPERTY_FILE_NAME = "settings";
    private final static ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);

    public static String getValue(String key) {
        return bundle.getString(key);
    }
}
