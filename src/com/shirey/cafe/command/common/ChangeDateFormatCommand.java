package com.shirey.cafe.command.common;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

public class ChangeDateFormatCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_DATE_FORMAT_STYLE = "dateFormatStyle";

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String dateFormatStyle = request.getParameter(PARAM_DATE_FORMAT_STYLE);
        request.getSession().setAttribute("dateFormatStyle", dateFormatStyle);

        return refreshForward(PageManager.getProperty(PAGE_PROFILE));

    }

}
