package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.DishType;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code AddDishCommand} class
 * is a command to add new dish to the database.
 *
 * @author Alex Shirey
 */

public class AddDishCommand implements Command {

    private static final String PAGE_DISHES = "page.dishes";
    private static final String PARAM_DISH_TYPE = "type";
    private static final String PARAM_DISH_NAME = "name";
    private static final String PARAM_DISH_DESCRIPTION = "description";
    private static final String PARAM_DISH_PRICE = "price";
    private static final String PARAM_DISH_IN_MENU = "inMenu";
    private AdminLogic adminLogic;

    public AddDishCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * Gets dish type, name, description, price, inMenu values from the request.
     * Validates this values, if input data is not valid, returns router to the same page with message about invalid input data.
     * Otherwise, creates and adds new dish to the database and returns router to the same page with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validateAddDishForm(String, String, String)
     * @see AdminLogic#addDish(DishType, String, String, BigDecimal, boolean)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String type = request.getParameter(PARAM_DISH_TYPE);
        String name = request.getParameter(PARAM_DISH_NAME);
        String description = request.getParameter(PARAM_DISH_DESCRIPTION);
        String price = request.getParameter(PARAM_DISH_PRICE);
        String inMenu = request.getParameter(PARAM_DISH_IN_MENU);

        if (!InputDataValidator.validateAddDishForm(name, description, price)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_DISHES));
        }

        Dish dish = adminLogic.addDish(DishType.valueOf(type), name, description, new BigDecimal(price), Boolean.valueOf(inMenu));
        List<Dish> dishes = (List<Dish>) request.getSession().getAttribute("dishes");
        dishes.add(dish);

        request.getSession().setAttribute("messageUpdatedSuccessfully", true);

        return refreshRedirect(PageManager.getProperty(PAGE_DISHES));

    }
}
