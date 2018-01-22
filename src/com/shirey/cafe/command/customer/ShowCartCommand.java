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

public class ShowCartCommand implements Command {

    private static final String PAGE_CART = "page.cart";
    private CustomerLogic customerLogic;

    public ShowCartCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

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
