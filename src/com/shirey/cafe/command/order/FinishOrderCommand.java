package com.shirey.cafe.command.order;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.OrderLogic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@code FinishOrderCommand} class
 * is a command to finish the order.
 *
 * @author Alex Shirey
 */

public class FinishOrderCommand implements Command {

    private static final String PARAM_ORDER_ID = "orderId";
    private OrderLogic orderLogic;

    public FinishOrderCommand(OrderLogic orderLogic) {
        this.orderLogic = orderLogic;
    }

    /**
     * Gets the order id from the request.
     * Checks, if the order status is not active (order can be auto cancelled, or cancelled/finished by admin before the customer),
     * prevents to finish it and returns router to the same page with a message.
     * Finishes the order (updates database with new values).
     * Returns router to the same page.
     * <p>
     * This command can be called both by admin and customer.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see OrderLogic#isOrderActive(Order)
     * @see OrderLogic#pickUpOrder(Order)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));

        List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findAny().orElse(null);

        String currentPage = CancelOrderCommand.defineCurrentPage(request);

        if (!orderLogic.isOrderActive(order)) {
            request.setAttribute("messageOrderIsAlreadyCancelled", true);
            return refreshForward(currentPage);
        }

        orderLogic.pickUpOrder(order);

        if ("customer".equals(request.getSession().getAttribute("role"))) {
            List<Order> activeOrders = (List) request.getSession().getAttribute("activeOrders");
            List<Order> finishedOrders = (List) request.getSession().getAttribute("finishedOrders");
            activeOrders.remove(order);
            finishedOrders.add(0, order);
            request.getSession().setAttribute("messageOrderPickedUp", true);
        } else {
            request.getSession().setAttribute("messageOrderFinished", true);
        }

        return refreshRedirect(currentPage);
    }
}
