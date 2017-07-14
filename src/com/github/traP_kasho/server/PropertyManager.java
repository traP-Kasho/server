package com.github.traP_kasho.server;

import java.util.ResourceBundle;

/**
 * Created by poispois on 2017/07/13.
 */
public final class PropertyManager {
    private static final String PROPERTY_FILE_NAME = "settings";
    private static ResourceBundle bundle;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
    }

    public static String getValue(String key) {
        return bundle.getString(key);
    }
}
