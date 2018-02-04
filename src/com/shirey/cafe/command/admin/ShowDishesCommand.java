package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@code ShowDishesCommand} class
 * is a command to show dishes page
 *
 * @author Alex Shirey
 */

public class ShowDishesCommand implements Command {

    private static final String PAGE_DISHES = "page.dishes";
    private AdminLogic adminLogic;

    public ShowDishesCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * Gets all dishes and dish types from the database,
     * sets session attributes to show this collections and
     * returns router to the dishes page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see AdminLogic#findAllDishes()
     * @see AdminLogic#findAllDishTypes()
     */
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
