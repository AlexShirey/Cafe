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
 * The {@code ShowReviewsCommand} class
 * is a command to show reviews page.
 *
 * @author Alex Shirey
 */

public class ShowReviewsCommand implements Command {

    private static final String PAGE_REVIEWS = "page.reviews";
    private OrderLogic orderLogic;
    private UserLogic userLogic;

    public ShowReviewsCommand(OrderLogic orderLogic, UserLogic userLogic) {
        this.orderLogic = orderLogic;
        this.userLogic = userLogic;
    }

    /**
     * Gets orders and customers with reviews from the database and
     * sets this values as session attributes, then
     * returns router to the reviews page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see OrderLogic#findOrdersWithReview()
     * @see UserLogic#findUsersWithReview()
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<Order> ordersWithReview = orderLogic.findOrdersWithReview();
        List<User> customersWithReview = userLogic.findUsersWithReview();

        request.getSession().setAttribute("ordersWithReview", ordersWithReview);
        request.getSession().setAttribute("customersWithReview", customersWithReview);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_REVIEWS));

        return router;

    }
}
