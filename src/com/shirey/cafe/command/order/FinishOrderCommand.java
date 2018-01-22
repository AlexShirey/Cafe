package com.shirey.cafe.command.order;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FinishOrderCommand implements Command {

    private static final String PAGE_CUSTOMER = "page.customer";
    private static final String PAGE_ADMIN_ORDERS = "page.orders";
    private static final String PARAM_ORDER_ID = "orderId";
    private OrderLogic orderLogic;

    public FinishOrderCommand(OrderLogic orderLogic) {
        this.orderLogic = orderLogic;
    }

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
