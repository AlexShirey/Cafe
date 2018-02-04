package com.shirey.cafe.command.order;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@code CancelOrderCommand} class
 * is a command to cancel the order.
 *
 * @author Alex Shirey
 */

public class CancelOrderCommand implements Command {

    private static final String PAGE_CUSTOMER = "page.customer";
    private static final String PAGE_ADMIN_ORDERS = "page.orders";
    private static final String PARAM_ORDER_ID = "orderId";
    private OrderLogic orderLogic;
    private UserLogic userLogic;

    public CancelOrderCommand(OrderLogic orderLogic, UserLogic userLogic) {
        this.orderLogic = orderLogic;
        this.userLogic = userLogic;
    }

    /**
     * Gets the order id from the request.
     * Checks, if the order status is not active (order can be auto cancelled, or cancelled/finished by admin before the customer),
     * prevents to cancel it and returns router to the same page with a message.
     * Cancels the order (subtracts loyalty points, updates database).
     * Checks, if the user has 3 cancelled orders, bans this user.
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
     * @see OrderLogic#cancelOrder(User, Order)
     * @see OrderLogic#getUserCancelledOrdersAmount(int)
     * @see UserLogic#findUserById(int)
     * @see UserLogic#banUser(User)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));

        List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findAny().orElse(null);

        String currentPage = defineCurrentPage(request);

        if (!orderLogic.isOrderActive(order)) {
            request.setAttribute("messageOrderIsAlreadyCancelled", true);
            return refreshForward(currentPage);
        }

        User user;
        if ("customer".equals(request.getSession().getAttribute("role"))) {
            user = (User) request.getSession().getAttribute("user");
        } else {
            user = userLogic.findUserById(order.getUserId());
        }

        orderLogic.cancelOrder(user, order);

        if (orderLogic.getUserCancelledOrdersAmount(order.getUserId()) == 3) {
            userLogic.banUser(user);
        }

        if ("customer".equals(request.getSession().getAttribute("role"))) {
            List<Order> activeOrders = (List) request.getSession().getAttribute("activeOrders");
            List<Order> cancelledOrders = (List) request.getSession().getAttribute("cancelledOrders");
            activeOrders.remove(order);
            cancelledOrders.add(0, order);
        }

        request.getSession().setAttribute("messageOrderCancelled", true);

        return refreshRedirect(currentPage);
    }

    /**
     * Defines from what page this command was called (depends on user role),
     * and returns this page
     *
     * @param request a request object
     * @return a current page
     */
    static String defineCurrentPage(HttpServletRequest request) {

        String currentPage;
        switch (((User) request.getSession().getAttribute("user")).getRole()) {
            case CUSTOMER:
                currentPage = PageManager.getProperty(PAGE_CUSTOMER);
                break;
            case ADMIN:
                currentPage = PageManager.getProperty(PAGE_ADMIN_ORDERS);
                break;
            default:
                currentPage = PageManager.getProperty(Command.PAGE_INDEX);
        }
        return currentPage;
    }

}
