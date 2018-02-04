package com.shirey.cafe.command.common;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.tag.FormatDateCustomTag;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ChangeDateFormatCommand} class
 * is a command to set date format style.
 *
 * @author Alex Shirey
 */

public class ChangeDateFormatCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_DATE_FORMAT_STYLE = "dateFormatStyle";

    /**
     * Gets date format style value from the request and
     * sets this value as session attribute (if the value is not null),
     * returns router to the same page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see FormatDateCustomTag
     */
    @Override
    public Router execute(HttpServletRequest request) {

        String dateFormatStyle = request.getParameter(PARAM_DATE_FORMAT_STYLE);
        if (dateFormatStyle != null) {
            request.getSession().setAttribute("dateFormatStyle", dateFormatStyle);
        }

        return refreshForward(PageManager.getProperty(PAGE_PROFILE));

    }

}
