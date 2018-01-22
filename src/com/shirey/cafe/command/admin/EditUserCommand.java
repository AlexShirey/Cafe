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

public class EditUserCommand implements Command {

    private static final String PAGE_EDIT_USER = "page.editUser";
    private static final String PARAM_POINTS = "points";
    private static final String PARAM_ACTIVE = "active";
    private static final String PARAM_ROLE = "role";
    private AdminLogic adminLogic;

    public EditUserCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

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
            if (adminLogic.HasCustomerActiveOrders(userToEdit)) {
                request.setAttribute("messageCantChangeRoleToAdmin", true);
                return refreshForward(PageManager.getProperty(PAGE_EDIT_USER));
            }
        }

        adminLogic.editUser(userToEdit, points, active, role);

        request.getSession().setAttribute("messageUpdatedSuccessfully", true);

        return refreshRedirect(PageManager.getProperty(PAGE_EDIT_USER));

    }
}

