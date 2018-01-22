package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowUsersCommand implements Command {

    private static final String PAGE_USERS = "page.users";
    private AdminLogic adminLogic;

    public ShowUsersCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<User> users = adminLogic.findAllUsers();
        request.getSession().setAttribute("users", users);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_USERS));

        return router;
    }
}
