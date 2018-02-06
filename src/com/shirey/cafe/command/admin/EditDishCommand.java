package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * The {@code EditDishCommand} class
 * is a command to edit a selected dish.
 *
 * @author Alex Shirey
 */

public class EditDishCommand implements Command {

    private static final String PAGE_EDIT_DISH = "page.editDish";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_PRICE = "price";
    private static final String PARAM_IN_MENU = "inMenu";
    private AdminLogic adminLogic;

    public EditDishCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * * Gets dish description, price, inMenu values from the request.
     * Validates input values, if input data is not valid, returns router to the same page with message about invalid input data.
     * Otherwise, edits dish and returns router to the same page with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validateEditDishForm(String, String)
     * @see AdminLogic#editDish(Dish, String, BigDecimal, boolean)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        Dish dishToEdit = (Dish) request.getSession().getAttribute("dishToEdit");

        String description = request.getParameter(PARAM_DESCRIPTION);
        String price = request.getParameter(PARAM_PRICE);
        String inMenu = request.getParameter(PARAM_IN_MENU);

        if (!InputDataValidator.validateEditDishForm(description.trim(), price)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_EDIT_DISH));
        }

        adminLogic.editDish(dishToEdit, description, new BigDecimal(price), Boolean.valueOf(inMenu));

        request.getSession().setAttribute("messageUpdatedSuccessfully", true);

        return refreshRedirect(PageManager.getProperty(PAGE_EDIT_DISH));

    }
}
