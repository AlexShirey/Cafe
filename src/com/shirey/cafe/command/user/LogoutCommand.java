package com.shirey.cafe.command.user;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code LogoutCommand} class
 * is a command to log out user from the system.
 *
 * @author Alex Shirey
 */

public class LogoutCommand implements Command {

    /**
     * Invalidates user session, then unbinds any objects bound to it.
     * Returns router to the main page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(HttpServletRequest request) {

        request.getSession().invalidate();
        return redirectToHomePage();
    }
}
