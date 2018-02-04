package com.shirey.cafe.command.dish;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The {@code ShowOrderDetailsCommand} class
 * is a command to show order details (what dishes are in the order) page.
 *
 * @author Alex Shirey
 */

public class ShowOrderDetailsCommand implements Command {

    private static final String PAGE_ORDER_DETAILS = "page.orderDetails";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_ORDER_PRICE = "orderPrice";
    private DishLogic dishLogic;

    public ShowOrderDetailsCommand(DishLogic dishLogic) {
        this.dishLogic = dishLogic;
    }

    /**
     * Gets order id and order price from the request,
     * finds dishes that are in this order,
     * sets them as session attribute and
     * returns router to the order details page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see DishLogic#findDishesInOrder(int)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String orderId = request.getParameter(PARAM_ORDER_ID);
        String orderPrice = request.getParameter(PARAM_ORDER_PRICE);

        Map<Dish, Integer> dishesInOrder = dishLogic.findDishesInOrder(Integer.parseInt(orderId));

        request.getSession().setAttribute("dishesInOrder", dishesInOrder);
        request.getSession().setAttribute("orderPrice", orderPrice);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_ORDER_DETAILS));

        return router;

    }


}
