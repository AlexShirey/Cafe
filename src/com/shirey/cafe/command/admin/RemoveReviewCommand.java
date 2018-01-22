package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RemoveReviewCommand implements Command {

    private static final String PAGE_REVIEWS = "page.reviews";
    private static final String PARAM_ORDER_ID = "orderId";
    private AdminLogic adminLogic;

    public RemoveReviewCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int orderId = Integer.parseInt(request.getParameter(PARAM_ORDER_ID));

        List<Order> orders = (List<Order>)request.getSession().getAttribute("ordersWithReview");
        Order order = orders.stream().filter(o -> o.getOrderId()== orderId).findAny().orElse(null);

        adminLogic.removeReview(order);

        orders.remove(order);

        request.getSession().setAttribute("messageReviewRemoved", true);

        return refreshRedirect(PageManager.getProperty(PAGE_REVIEWS));

    }
}
