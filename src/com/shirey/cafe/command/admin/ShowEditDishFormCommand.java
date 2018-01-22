package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

public class ShowEditDishFormCommand implements Command {

    private static final String PAGE_EDIT_DISH = "page.editDish";
    private static final String PARAM_DISH_TO_EDIT_ID = "dishToEditId";
    private DishLogic dishLogic;

    public ShowEditDishFormCommand(DishLogic dishLogic) {
        this.dishLogic = dishLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int dishToEditId = Integer.parseInt(request.getParameter(PARAM_DISH_TO_EDIT_ID));

        Dish dishToEdit = dishLogic.findDishById(dishToEditId);

        request.getSession().setAttribute("dishToEdit", dishToEdit);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_EDIT_DISH));

        return router;
    }
}
