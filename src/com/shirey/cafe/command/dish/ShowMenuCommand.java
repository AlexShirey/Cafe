package com.shirey.cafe.command.dish;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code ShowMenuCommand} class
 * is a command to show menu page.
 *
 * @author Alex Shirey
 */

public class ShowMenuCommand implements Command {

    private static final String PAGE_MENU = "page.menu";
    private DishLogic dishLogic;

    public ShowMenuCommand(DishLogic dishLogic) {
        this.dishLogic = dishLogic;
    }

    /**
     * Gets dishes presented in the menu from database as list,
     * sorts this list by dish type, sets it as session attribute and
     * returns router to the menu page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see DishLogic#findDishesInMenu()
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<Dish> menu = dishLogic.findDishesInMenu();
        menu.sort(Comparator.comparingInt(o -> o.getType().getDishTypeId()));

        request.getSession().setAttribute("menu", menu);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_MENU));

        return router;
    }
}
