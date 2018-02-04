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
 * The {@code ShowCartCommand} class
 * is a command to show customer's cart page.
 *
 * @author Alex Shirey
 */

public class ShowCartCommand implements Command {

    private static final String PAGE_CART = "page.cart";
    private CustomerLogic customerLogic;

    public ShowCartCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    /**
     * Gets a customer's shopping cart from the session attribute,
     * defines its total price and sets this value as another session attribute,
     * returns router to the cart page.
     * If the cart is empty, the price isn't calculated.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see CustomerLogic#defineCartPrice(Map)
     */
    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_CART));

        Map<Dish, Integer> cart = (LinkedHashMap<Dish, Integer>) request.getSession().getAttribute("cart");

        if (cart.isEmpty()) {
            return router;
        }

        BigDecimal cartPrice = customerLogic.defineCartPrice(cart);
        request.getSession().setAttribute("cartPrice", cartPrice);

        return router;
    }
}
