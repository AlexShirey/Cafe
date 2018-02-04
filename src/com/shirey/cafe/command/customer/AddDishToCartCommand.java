package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.DishType;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code AddDishToCartCommand} class
 * is a command to add dish to the customer's shopping cart.
 *
 * @author Alex Shirey
 */

public class AddDishToCartCommand implements Command {

    private static final String PAGE_MENU = "page.menu";
    private static final String PARAM_DISH_ID = "dishId";
    private static final String PARAM_DISH_QUANTITY = "dishQuantity";
    private CustomerLogic customerLogic;

    public AddDishToCartCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    /**
     * Gets dish id, dish quantity values from the request,
     * validates quantity, if value is not a positive number, return router to the same page with message about invalid quantity.
     * Otherwise, add founded dish and its quantity to the cart with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see InputDataValidator#isPositiveNumber(String)
     * @see CustomerLogic#addDishToCart(Map, Dish, int)
     */
    @Override
    public Router execute(HttpServletRequest request) {

        List<Dish> menu = (List<Dish>) request.getSession().getAttribute("menu");
        Map<Dish, Integer> cart = (LinkedHashMap<Dish, Integer>) request.getSession().getAttribute("cart");

        int dishId = Integer.parseInt(request.getParameter(PARAM_DISH_ID));
        String quantity = request.getParameter(PARAM_DISH_QUANTITY);

        request.getSession().setAttribute("trLabel", dishId);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_MENU) + "#jumpTag" + dishId);

        if (!InputDataValidator.isPositiveNumber(quantity)) {
            request.getSession().setAttribute("messageInvalidQuantity", true);
            return router;
        }

        Dish dishToAdd = menu.stream().filter(dish -> dishId == dish.getDishId()).findAny().orElse(null);
        customerLogic.addDishToCart(cart, dishToAdd, Integer.parseInt(quantity));

        request.getSession().setAttribute("messageDishAddedToCart", true);

        router.setRoute(Router.RouteType.REDIRECT);
        return router;

    }

}
