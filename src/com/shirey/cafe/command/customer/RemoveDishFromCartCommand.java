package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code RemoveDishFromCartCommand} class
 * is a command to remove a dish from the customer's shopping cart.
 *
 * @author Alex Shirey
 */

public class RemoveDishFromCartCommand implements Command {

    private static final String PAGE_CART = "page.cart";
    private static final String PARAM_REMOVED_DISH_ID = "dishId";
    private CustomerLogic customerLogic;

    public RemoveDishFromCartCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    /**
     * Gets dish id from the request,
     * removes the dish from the shopping cart and
     * recalculates cart price.
     * Returns router to the same page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see CustomerLogic#removeDishFromCart(Map, int)
     * @see CustomerLogic#defineCartPrice(Map)
     */
    @Override
    public Router execute(HttpServletRequest request) {

        Map<Dish, Integer> cart = (LinkedHashMap<Dish, Integer>) request.getSession().getAttribute("cart");

        int removedDishId = Integer.parseInt(request.getParameter(PARAM_REMOVED_DISH_ID));

        customerLogic.removeDishFromCart(cart, removedDishId);

        BigDecimal cartPrice = customerLogic.defineCartPrice(cart);
        request.getSession().setAttribute("cartPrice", cartPrice);

        return refreshRedirect(PageManager.getProperty(PAGE_CART));
    }
}
