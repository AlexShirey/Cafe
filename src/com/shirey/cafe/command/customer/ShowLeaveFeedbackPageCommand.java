package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ShowLeaveFeedbackPageCommand} class
 * is a command to show leave feedback page.
 *
 * @author Alex Shirey
 */

public class ShowLeaveFeedbackPageCommand implements Command {

    private static final String PAGE_LEAVE_FEEDBACK = "page.leaveFeedback";

    /**
     * Gets order id (to which feedback should be left) parameter from the request,
     * sets this id as session attribute,
     * returns router to the leave feedback page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(HttpServletRequest request) {

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        request.getSession().setAttribute("orderId", orderId);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_LEAVE_FEEDBACK));

        return router;
    }
}
