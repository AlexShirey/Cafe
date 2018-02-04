package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.entity.UserRole;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * The {@code EditUserCommand} class
 * is a command to edit a selected user.
 *
 * @author Alex Shirey
 */

public class EditUserCommand implements Command {

    private static final String PAGE_EDIT_USER = "page.editUser";
    private static final String PARAM_POINTS = "points";
    private static final String PARAM_ACTIVE = "active";
    private static final String PARAM_ROLE = "role";
    private AdminLogic adminLogic;

    public EditUserCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * Gets loyalty points, active status and user role values from the request.
     * Validates points to be a positive number, if value is not valid,
     * returns router to the same page with message about invalid quantity.
     * Otherwise, edits user and returns router to the same page with success message.
     * <p>
     * Not allows to change user role from 'customer' to 'admin' if this customer has active orders.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#isPositiveNumberOrZero(String)
     * @see AdminLogic#hasCustomerActiveOrders(User)
     * @see AdminLogic#editUser(User, BigDecimal, boolean, UserRole)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        User userToEdit = (User) request.getSession().getAttribute("userToEdit");

        String points = request.getParameter(PARAM_POINTS);
        String active = request.getParameter(PARAM_ACTIVE);
        String role = request.getParameter(PARAM_ROLE);

        if (!InputDataValidator.isPositiveNumberOrZero(points)) {
            request.setAttribute("messageInvalidQuantity", true);
            return refreshForward(PageManager.getProperty(PAGE_EDIT_USER));
        }

        if (userToEdit.getRole() != UserRole.ADMIN && "admin".equals(role)) {
            if (adminLogic.hasCustomerActiveOrders(userToEdit)) {
                request.setAttribute("messageCantChangeRoleToAdmin", true);
                return refreshForward(PageManager.getProperty(PAGE_EDIT_USER));
            }
        }

        adminLogic.editUser(userToEdit, new BigDecimal(points), Boolean.valueOf(active), UserRole.valueOf(role.toUpperCase()));

        request.getSession().setAttribute("messageUpdatedSuccessfully", true);

        return refreshRedirect(PageManager.getProperty(PAGE_EDIT_USER));

    }
}

