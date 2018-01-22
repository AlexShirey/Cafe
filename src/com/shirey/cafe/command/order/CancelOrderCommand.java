package com.shirey.cafe.command.order;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.entity.UserRole;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));

        List<Order> orders = (List<Order>) request.getSession().getAttribute("orders");
        Order order = orders.stream().filter(o -> o.getOrderId() == orderId).findAny().orElse(null);

        String currentPage = defineCurrentPage(request);

        if (order.getStatus() != Order.Status.ACTIVE) {
            request.setAttribute("messageOrderIsAlreadyCancelled", true);
            return refreshForward(currentPage);
        }

        User user = userLogic.findUserById(order.getUserId());

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


    private String defineCurrentPage(HttpServletRequest request) {

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
