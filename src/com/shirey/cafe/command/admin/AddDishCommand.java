package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

        Dish dish = adminLogic.addDish(type, name, description, price, inMenu);
        List<Dish> dishes = (List<Dish>) request.getSession().getAttribute("dishes");
        dishes.add(dish);

        request.getSession().setAttribute("messageUpdatedSuccessfully", true);

        return refreshRedirect(PageManager.getProperty(PAGE_DISHES));

    }
}
