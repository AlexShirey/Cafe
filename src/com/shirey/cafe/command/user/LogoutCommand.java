package com.shirey.cafe.command.user;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        request.getSession().invalidate();
        return redirectToHomePage();
    }
}
