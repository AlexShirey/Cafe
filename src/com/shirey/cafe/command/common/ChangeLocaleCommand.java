package com.shirey.cafe.command.common;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {

    private static final String PARAM_LOCALE = "locale";
    private static final String ATTR_LOCALE = "locale";

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String locale = request.getParameter(PARAM_LOCALE);
        request.getSession().setAttribute(ATTR_LOCALE, locale);

        return refreshForward((String) request.getSession().getAttribute("currentPage"));

    }

}
