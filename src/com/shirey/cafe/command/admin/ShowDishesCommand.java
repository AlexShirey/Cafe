package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowDishesCommand implements Command {

    private static final String PAGE_DISHES = "page.dishes";
    private AdminLogic adminLogic;

    public ShowDishesCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<Dish> dishes = adminLogic.findAllDishes();
        request.getSession().setAttribute("dishes", dishes);

        List<String> dishTypes = adminLogic.findAllDishTypes();
        request.getSession().setAttribute("dishTypes", dishTypes);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_DISHES));

        return router;

    }
}
