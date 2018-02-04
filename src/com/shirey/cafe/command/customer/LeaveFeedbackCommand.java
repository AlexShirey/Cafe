package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code LeaveFeedbackCommand} class
 * is a command to leave a feedback to the order.
 *
 * @author Alex Shirey
 */

public class LeaveFeedbackCommand implements Command {

    private static final String PAGE_LEAVE_FEEDBACK = "page.leaveFeedback";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_RATING = "rating";
    private static final String PARAM_REVIEW = "review";

    private OrderLogic orderLogic;

    public LeaveFeedbackCommand(OrderLogic orderLogic) {
        this.orderLogic = orderLogic;
    }

    /**
     * Gets order id (to which feedback should be left), rating and review from the request,
     * leaves feedback (updates database with this values) and
     * returns router with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see OrderLogic#leaveFeedback(int, int, String)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));
        int rating = Integer.parseInt(request.getParameter(PARAM_RATING));
        String review = request.getParameter(PARAM_REVIEW);

        orderLogic.leaveFeedback(orderId, rating, review);

        request.getSession().setAttribute("messageFeedbackLeft", true);

        return refreshRedirect(PageManager.getProperty(PAGE_LEAVE_FEEDBACK));
    }
}
