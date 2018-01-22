package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowEditUserFormCommand implements Command {

    private static final String PAGE_EDIT_USER = "page.editUser";
    private static final String PARAM_USER_TO_EDIT_ID = "userToEditId";
    private UserLogic userLogic = new UserLogic();
    private AdminLogic adminLogic = new AdminLogic();

    public ShowEditUserFormCommand(UserLogic userLogic, AdminLogic adminLogic) {
        this.userLogic = userLogic;
        this.adminLogic = adminLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        int userToEditId = Integer.parseInt(request.getParameter(PARAM_USER_TO_EDIT_ID));

        User userToEdit = userLogic.findUserById(userToEditId);
        List<String> userRoles = adminLogic.findAllUserRoles();

        request.getSession().setAttribute("userToEdit", userToEdit);
        request.getSession().setAttribute("userRoles", userRoles);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_EDIT_USER));

        return router;

    }
}
