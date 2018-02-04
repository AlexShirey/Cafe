package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@code RemoveReviewCommand} class
 * is a command to remove to remove order's review.
 *
 * @author Alex Shirey
 */

public class RemoveReviewCommand implements Command {

    private static final String PAGE_REVIEWS = "page.reviews";
    private static final String PARAM_ORDER_ID = "orderId";
    private AdminLogic adminLogic;

    public RemoveReviewCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * Gets order id from the request.
     * Finds the order by id and sets its rating and review values to null.
     * Returns router to the same page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see AdminLogic#removeReview(Order)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));

        List<Order> orders = (List<Order>) request.getSession().getAttribute("ordersWithReview");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findAny().orElse(null);

        adminLogic.removeReview(order);

        orders.remove(order);

        request.getSession().setAttribute("messageReviewRemoved", true);

        return refreshRedirect(PageManager.getProperty(PAGE_REVIEWS));

    }
}
