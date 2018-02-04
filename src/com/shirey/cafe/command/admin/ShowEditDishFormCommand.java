package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ShowEditDishFormCommand} class
 * is a command to edit dish.
 *
 * @author Alex Shirey
 */

public class ShowEditDishFormCommand implements Command {

    private static final String PAGE_EDIT_DISH = "page.editDish";
    private static final String PARAM_DISH_TO_EDIT_ID = "dishToEditId";
    private DishLogic dishLogic;

    public ShowEditDishFormCommand(DishLogic dishLogic) {
        this.dishLogic = dishLogic;
    }

    /**
     * Gets dish to edit id parameter from the request,
     * finds the dish with this id and
     * sets this dish as session attribute,
     * returns router to the edit dish page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see DishLogic#findDishById(int)
     */
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
