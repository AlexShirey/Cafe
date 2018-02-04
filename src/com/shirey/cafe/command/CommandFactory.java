package com.shirey.cafe.command;

import com.shirey.cafe.exception.UnsupportedCommandException;

/**
 * The {@code CommandFactory} class
 * is a factory that defines and returns {@code Command} object.
 *
 * @author Alex Shirey
 */

public class CommandFactory {

    /**
     * Defines and returns {@code Command} object.
     *
     * @param value a value from which {@code Command} object is defined.
     * @return {@code Command} object.
     * @throws UnsupportedCommandException if value is null, empty or not presented in {@code CommandType} class.
     */
    public static Command defineCommand(String value) throws UnsupportedCommandException {

        if (value == null || value.isEmpty()) {
            throw new UnsupportedCommandException("command in jsp is null or empty.");
        }
        try {
            return CommandType.valueOf(value.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            throw new UnsupportedCommandException("command \"" + value + "\" in jsp is not supported.", e);
        }
    }

}
