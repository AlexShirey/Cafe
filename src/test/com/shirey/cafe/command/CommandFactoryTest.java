package com.shirey.cafe.command;

import com.shirey.cafe.command.guest.LoginCommand;
import com.shirey.cafe.exception.UnsupportedCommandException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CommandFactoryTest {

    @Test
    public void testDefineCommand() throws UnsupportedCommandException {

        Class actual = CommandFactory.defineCommand("login").getClass();
        Class expected = LoginCommand.class;
        Assert.assertEquals(actual, expected);
    }

    @Test (expectedExceptions = UnsupportedCommandException.class)
    public void testDefineCommandException() throws UnsupportedCommandException {

        CommandFactory.defineCommand("bla bla bla");
    }

}