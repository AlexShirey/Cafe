package com.shirey.cafe.util;

import java.util.regex.Pattern;

/**
 * The {@code InputDataValidator} class contains methods for
 * validating an input data (data from forms).
 * <p>
 * All represented methods in this class are static.
 *
 * @author Alex Shirey
 */

public class InputDataValidator {

    /**
     * The {@code String} value that is pattern for login.
     */
    private static final String LOGIN_PATTERN = "[a-z0-9._%+-]{1,20}@[a-z0-9.-]{1,16}\\.[a-z]{2,3}";
    /**
     * The {@code String} value that is pattern for password.
     */
    private static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    /**
     * The {@code String} value that is pattern for names - first name, last name.
     */
    private static final String NAME_PATTERN = "[A-Z][a-z]{1,19}|[А-ЯЁ][а-яё]{1,19}";
    /**
     * The {@code String} value that is pattern for user phone.
     */
    private static final String PHONE_PATTERN = "[+]\\d{3}[(]\\d{2}[)]\\d{3}[-]\\d{2}[-]\\d{2}";
    /**
     * The {@code String} value that is pattern for positive numbers.
     */
    private static final String NUMBER_PATTERN = "\\d+(\\.\\d+)?";
    /**
     * The {@code String} value that is pattern for dish name.
     */
    private static final String DISH_NAME_PATTERN = "[A-ZА-ЯЁa-zа-яё\\d ]{2,50}";
    /**
     * The {@code String} value that is pattern for description.
     */
    private static final String DISH_DESCRIPTION_PATTERN = "[A-ZА-ЯЁ].{4,200}";

    /**
     * Don't let anyone instantiate this class.
     */
    private InputDataValidator() {
    }

    /**
     * Validates input data for login form - login, password. Checks if the params match patterns.
     *
     * @param login    login input value.
     * @param password password input value.
     * @return {@code true} if, and only if, both of params match patterns, otherwise
     * {@code false}
     */
    public static boolean validateLoginForm(String login, String password) {

        return Pattern.matches(LOGIN_PATTERN, login) && Pattern.matches(PASSWORD_PATTERN, password);
    }

    /**
     * Validates input data for registration form. Checks if the params match patterns.
     *
     * @param login     login input value.
     * @param password  password input value.
     * @param firstName first name input value.
     * @param lastName  last name input value.
     * @param phone     phone input value.
     * @return {@code true} if, and only if, all params match patterns, otherwise
     * {@code false}
     */
    public static boolean validateRegistrationForm(String login, String password, String firstName, String lastName, String phone) {

        if (!Pattern.matches(LOGIN_PATTERN, login)) {
            return false;
        }
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            return false;
        }
        if (!Pattern.matches(NAME_PATTERN, firstName)) {
            return false;
        }
        if (!Pattern.matches(NAME_PATTERN, lastName)) {
            return false;
        }
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    /**
     * Validates input data for add dish form - name, description, and price. Checks if the params match patterns.
     *
     * @param name        dish name input value.
     * @param description dish description input value.
     * @param price       dish price input value.
     * @return {@code true} if, and only if, all params match patterns
     * (description can be empty {@code String}, price should be positive number), otherwise
     * {@code false}
     */
    public static boolean validateAddDishForm(String name, String description, String price) {

        if (!Pattern.matches(DISH_NAME_PATTERN, name)) {
            return false;
        }
        if (!description.isEmpty() && !Pattern.matches(DISH_DESCRIPTION_PATTERN, description)) {
            return false;
        }
        return isPositiveNumber(price);
    }

    /**
     * Validates input data for edit dish form - description and price. Checks if the params match patterns.
     *
     * @param description description input value.
     * @param price       price input value.
     * @return {@code true} if, and only if, all params match patterns
     * (description can be empty {@code String}, price should be positive number), otherwise
     * {@code false}
     */
    public static boolean validateEditDishForm(String description, String price) {

        if (!description.isEmpty() && !Pattern.matches(DISH_DESCRIPTION_PATTERN, description)) {
            return false;
        }
        return isPositiveNumber(price);
    }

    /**
     * Validates input password, if it matches pattern.
     *
     * @param password password input value.
     * @return {@code true} if password matches pattern,
     * otherwise {@code false}
     */
    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    /**
     * Validates input name, if it matches pattern.
     *
     * @param name name input value.
     * @return {@code true} if name matches pattern,
     * otherwise {@code false}
     */
    public static boolean validateName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }

    /**
     * Validates input phone, if it matches pattern.
     *
     * @param phone phone input value.
     * @return {@code true} if phone matches pattern,
     * otherwise {@code false}
     */
    public static boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    /**
     * Validates input value, checks if this value is a positive number.
     *
     * @param value a value.
     * @return {@code true} if a value is a positive number,
     * otherwise {@code false}
     */
    public static boolean isPositiveNumber(String value) {

        return Pattern.matches(NUMBER_PATTERN, value) && Double.parseDouble(value) > 0;
    }

    /**
     * Validates input value, checks if this value is a positive number or zero.
     *
     * @param value a value.
     * @return {@code true} if a value is a positive number or zero,
     * otherwise {@code false}
     */
    public static boolean isPositiveNumberOrZero(String value) {

        return Pattern.matches(NUMBER_PATTERN, value) && Double.parseDouble(value) >= 0;
    }
}