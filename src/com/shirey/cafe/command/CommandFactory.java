package com.shirey.cafe.command;

import com.shirey.cafe.exception.UnsupportedCommandException;

public class CommandFactory {

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
