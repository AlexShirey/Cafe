package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCustomerPageCommand implements Command {

    private static final String PAGE_CUSTOMER = "page.customer";
    private OrderLogic orderLogic;

    public ShowCustomerPageCommand(OrderLogic orderLogic) {
        this.orderLogic = orderLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        User user = (User) request.getSession().getAttribute("user");
        List<Order> orders = orderLogic.findUserOrders(user.getUserId());

        List<Order> activeOrders = orders.stream().filter(order -> order.getStatus() == Order.Status.ACTIVE).collect(Collectors.toList());
        List<Order> cancelledOrders = orders.stream().filter(order -> order.getStatus() == Order.Status.CANCELLED).collect(Collectors.toList());
        List<Order> finishedOrders = orders.stream().filter(order -> order.getStatus() == Order.Status.FINISHED).collect(Collectors.toList());

        request.getSession().setAttribute("orders", orders);
        request.getSession().setAttribute("activeOrders", activeOrders);
        request.getSession().setAttribute("cancelledOrders", cancelledOrders);
        request.getSession().setAttribute("finishedOrders", finishedOrders);
        request.getSession().setAttribute("currentTimeMillis", System.currentTimeMillis());

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_CUSTOMER));

        return router;

    }
}
