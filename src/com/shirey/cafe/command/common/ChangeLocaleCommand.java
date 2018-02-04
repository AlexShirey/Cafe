package com.shirey.cafe.command.common;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ChangeLocaleCommand} class
 * is a command to set locale.
 *
 * @author Alex Shirey
 */

public class ChangeLocaleCommand implements Command {

    private static final String PARAM_LOCALE = "locale";
    private static final String ATTR_LOCALE = "locale";

    /**
     * Gets locale value from the request and
     * sets this value as session attribute (if the value is not null),
     * returns router to the same page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(HttpServletRequest request) {

        String locale = request.getParameter(PARAM_LOCALE);
        if (locale != null) {
            request.getSession().setAttribute(ATTR_LOCALE, locale);
        }

        return refreshForward((String) request.getSession().getAttribute("currentPage"));

    }
}
