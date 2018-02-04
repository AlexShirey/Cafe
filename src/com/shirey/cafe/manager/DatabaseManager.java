package com.shirey.cafe.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The {@code DatabaseManager} class
 * is a manager that gets a string from
 * database configuration property file.
 *
 * @author Alex Shirey
 */

public class DatabaseManager {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);

    /**
     * property file path and name
     */
    private static final String DB_CONFIG_PATH = "resources.configuration.database";
    private static ResourceBundle resourceBundle;

    /**
     * Don't let anyone instantiate this class.
     */
    private DatabaseManager() {
    }

    /**
     * Gets a string for the given key from this resource bundle.
     * <p>
     * On the first call, this method calls private method init() to initialise current resource bundle.
     *
     * @param key a key that should be found in the property file.
     * @return the string for the given key
     * @throws NullPointerException if <code>key</code> is <code>null</code>
     * @throws ClassCastException   if the object found for the given key is not a string
     * @throws RuntimeException     if no resource bundle for the specified base name can be found or
     *                              if no object for the given key can be found
     */
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

    /**
     * Init current resource bundle.
     *
     * @throws RuntimeException if no resource bundle for the specified base name can be found
     */
    private static void init() {
        try {
            resourceBundle = ResourceBundle.getBundle(DB_CONFIG_PATH);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
            throw new RuntimeException("couldn't load resources, no resource bundle for the specified base name can be found", e);
        }
    }

}
