package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveDishFromCartCommand implements Command {

    private static final String PAGE_CART = "page.cart";
    private static final String PARAM_REMOVED_DISH_ID = "dishId";
    private CustomerLogic customerLogic;

    public RemoveDishFromCartCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        Map<Dish, Integer> cart = (LinkedHashMap<Dish, Integer>) request.getSession().getAttribute("cart");

        int removedDishId = Integer.parseInt(request.getParameter(PARAM_REMOVED_DISH_ID));

        customerLogic.removeDishFromCart(cart, removedDishId);

        BigDecimal cartPrice = customerLogic.defineCartPrice(cart);
        request.getSession().setAttribute("cartPrice", cartPrice);

        return refreshRedirect(PageManager.getProperty(PAGE_CART));
    }
}
