package com.shirey.cafe.command.order;

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


public class ShowReviewsCommand implements Command {

    private static final String PAGE_REVIEWS = "page.reviews";
    private OrderLogic orderLogic;
    private CustomerLogic customerLogic;

    public ShowReviewsCommand(OrderLogic orderLogic, CustomerLogic customerLogic) {
        this.orderLogic = orderLogic;
        this.customerLogic = customerLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<Order> ordersWithReview = orderLogic.findOrdersWithReview();
        List<User> customersWithReview = customerLogic.findCustomersWithReview();

        request.getSession().setAttribute("ordersWithReview", ordersWithReview);
        request.getSession().setAttribute("customersWithReview", customersWithReview);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_REVIEWS));

        return router;

    }
}
