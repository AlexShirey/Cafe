package com.shirey.cafe.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PageManager {

    private static final Logger LOGGER = LogManager.getLogger(PageManager.class);
    private static final String PAGE_PATH = "resources.configuration.pages";
    private static ResourceBundle resourceBundle;

    private PageManager() {
    }

    public static String getProperty(String key) {
        if (resourceBundle == null) {
            init();
        }

        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            throw new RuntimeException("couldn't load resources, no object for the given key can be found", e);
        }
    }


    private static void init() {
        try {
            resourceBundle = ResourceBundle.getBundle(PAGE_PATH);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            throw new RuntimeException("couldn't load resources, no resource bundle for the specified base name can be found", e);
        }
    }

}
