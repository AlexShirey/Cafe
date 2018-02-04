package com.shirey.cafe.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.shirey.cafe.util.InputDataValidator.*;


public class InputDataValidatorTest {

    @DataProvider(name = "loginFormProvider")
    private static Object[][] loginFormProvider() {
        return new Object[][]{
                {"miuichobo@gmail.com", "qwQW12", true},
                {"as_09@mail.ru", "12qwQW", true},
                {"00misha_1984@gmail.com", "it's a sEcret_pass1!", true},
                {"misha.1984@gmail.com", "it's a sEcret_pass1!", true},
                {"olga.star@mail.ru", "qQasdasd5!", true},
                {"test.666@gmail.com", "sdfD4!", true},
                {"youremail@gmai.com", " q Q 1 ", true},
                {"121.mail.cccc", "qqQQ!", false},
                {"Max@gmail.com", "Max", false},
                {"1984@mail", "it's a sEcret_pass!", false},
                {" ", " ", false},
                {"", "", false}
        };
    }

    @Test(dataProvider = "loginFormProvider")
    public void testValidateLoginForm(String login, String password, boolean expected) {

        Assert.assertEquals(validateLoginForm(login, password), expected);
    }

    @Test(dependsOnGroups = "registrationForm")
    public void testValidateRegistrationForm() {

        String login = "miuichobo@gmail.com";
        String password = "qwQW12";
        String firstName = "Alex";
        String lastName = "Shirey";
        String phone = "+375(29)612-61-09";
        Assert.assertTrue(validateRegistrationForm(login, password, firstName, lastName, phone));
    }

    @DataProvider(name = "passwordProvider")
    private static Object[][] passwordProvider() {
        return new Object[][]{
                {"qwQW12", true},
                {"12qwQW", true},
                {"it's a sEcret_pass1!", true},
                {"qQasdasd5!", true},
                {"sdfD4!", true},
                {" q Q 1 ", true},
                {"qqQQ!", false},
                {"Max", false},
                {"it's a sEcret_pass!", false},
                {" ", false},
                {"", false}
        };
    }

    @Test(dataProvider = "passwordProvider", groups = "registrationForm")
    public void testValidatePassword(String password, boolean expected) {

        Assert.assertEquals(validatePassword(password), expected);
    }

    @DataProvider(name = "nameProvider")
    private static Object[][] nameProvider() {
        return new Object[][]{
                {"Миша", true},
                {"Max", true},
                {"Smith", true},
                {"миша", false},
                {"МишА", false},
                {"Мишаsmith", false},
                {"Мишаааааааааааааааааааааааааааа", false},
                {"Z", false},
                {" ", false},
                {"", false}
        };
    }

    @Test(dataProvider = "nameProvider", groups = "registrationForm")
    public void testValidateName(String name, boolean expected) {

        Assert.assertEquals(validateName(name), expected);
    }

    @DataProvider(name = "phoneProvider")
    private static Object[][] phoneProvider() {
        return new Object[][]{
                {"+375(29)612-61-09", true},
                {"+375(44)555-66-77", true},
                {"+375(129)612-61-09", false},
                {"+375(29)612-61-0.", false},
                {"+375(29)612+61-09", false},
                {"375(29)612-61-09", false},
                {"+375 (29) 612-61-09", false}
        };
    }

    @Test(dataProvider = "phoneProvider", groups = "registrationForm")
    public void testValidatePhone(String phone, boolean expected) {

        Assert.assertEquals(validatePhone(phone), expected);
    }

    @DataProvider(name = "addDishFormProvider")
    private static Object[][] addDishFormProvider() {
        return new Object[][]{
                {"Olivier", "Very tasty salad", "3.50", true},
                {"Olivier with Chips", "Very tasty salad with chips!", "4.00", true},
                {"4seasons pizza", "Special edition with hot chilly pepper! :)", "6.00", true},
                {"45 eggs", "", "5.50", true},
                {"4seasons pizza", "sdas", "0.50", false},
                {"4seasons pizza", "Just try it!", "0", false},
                {"", "", "", false}
        };
    }


    @Test(dataProvider = "addDishFormProvider")
    public void testValidateAddDishForm(String name, String description, String price, boolean expected) {

        Assert.assertEquals(validateAddDishForm(name, description, price), expected);
    }

    @DataProvider(name = "editDishFormProvider")
    private static Object[][] editDishFormProvider() {
        return new Object[][]{
                {"Very tasty salad", "3.50", true},
                {"Very tasty salad with chips!", "4.00", true},
                {"Special edition with hot chilly pepper! :)", "6.00", true},
                {"", "5.50", true},
                {"sdas", "0.50", false},
                {"Just try it!", "0", false},
                {"", "", false}
        };
    }

    @Test(dataProvider = "editDishFormProvider")
    public void testValidateEditDishForm(String description, String price, boolean expected) {

        Assert.assertEquals(validateEditDishForm(description, price), expected);
    }


    @DataProvider(name = "positiveNumberProvider")
    private static Object[][] positiveNumberProvider() {
        return new Object[][]{
                {"0.01", true},
                {"1", true},
                {"100", true},
                {"55.55555555", true},
                {"not a number", false},
                {"word", false},
                {"", false},
                {"-5", false},
                {"-0.55", false},
                {"-0,5", false},
                {"0", false}
        };
    }

    @Test(dataProvider = "positiveNumberProvider")
    public void testIsPositiveNumber(String positiveNumber, boolean expected) {

        Assert.assertEquals(isPositiveNumber(positiveNumber), expected);
    }


    @Test
    public void testIsPositiveNumberOrZero() {

        String zero = "0";
        Assert.assertTrue(isPositiveNumberOrZero(zero));
    }

}