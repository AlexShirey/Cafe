package com.shirey.cafe.util;

import java.util.regex.Pattern;

public class InputDataValidator {

    private static final String LOGIN_PATTERN = "[a-z0-9._%+-]{1,20}@[a-z0-9.-]{1,16}\\.[a-z]{2,3}";
    private static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    private static final String NAME_PATTERN = "[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}";
    private static final String PHONE_PATTERN = "[+]\\d{3}[(]\\d{2}[)]\\d{3}[-]\\d{2}[-]\\d{2}";
    private static final String NUMBER_PATTERN = "\\d+(\\.\\d+)?";
    private static final String DISH_NAME_PATTERN = "[A-ZА-ЯЁa-zа-яё\\d ]{2,}";
    private static final String DISH_DESCRIPTION_PATTERN = "[A-ZА-ЯЁ].{4,}";


    private InputDataValidator() {
    }

    public static boolean validateLoginForm(String login, String password) {

        if (!Pattern.matches(LOGIN_PATTERN, login)) {
            return false;
        }
        return Pattern.matches(PASSWORD_PATTERN, password);
    }


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

    public static boolean validateAddDishForm(String name, String description, String price) {

        if (!Pattern.matches(DISH_NAME_PATTERN, name)) {
            return false;
        }
        if (!description.isEmpty() && !Pattern.matches(DISH_DESCRIPTION_PATTERN, description)) {
            return false;
        }
        return isPositiveNumber(price);
    }

    public static boolean validateEditDishForm(String description, String price) {

        if (!description.isEmpty() && !Pattern.matches(DISH_DESCRIPTION_PATTERN, description)) {
            return false;
        }
        return isPositiveNumber(price);
    }


    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    public static boolean validateName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }

    public static boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_PATTERN, phone);
    }


    public static boolean isPositiveNumber(String value) {

        return Pattern.matches(NUMBER_PATTERN, value) ? Double.parseDouble(value) > 0 : false;

    }

    public static boolean isPositiveNumberOrZero(String value) {

        return Pattern.matches(NUMBER_PATTERN, value) ? Double.parseDouble(value) >= 0 : false;

    }
}






