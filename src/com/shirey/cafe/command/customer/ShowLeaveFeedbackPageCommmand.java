package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

public class ShowLeaveFeedbackPageCommmand implements Command {

    private static final String PAGE_LEAVE_FEEDBACK = "page.leaveFeedback";

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        request.getSession().setAttribute("orderId", orderId);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_LEAVE_FEEDBACK));

        return router;
    }
}
