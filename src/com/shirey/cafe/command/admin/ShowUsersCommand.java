package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@code ShowUsersCommand} class
 * is a command to show users page.
 *
 * @author Alex Shirey
 */

public class ShowUsersCommand implements Command {

    private static final String PAGE_USERS = "page.users";
    private AdminLogic adminLogic;

    public ShowUsersCommand(AdminLogic adminLogic) {
        this.adminLogic = adminLogic;
    }

    /**
     * Gets all users from the database,
     * sets the session attribute to show them and
     * returns router to the users page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see AdminLogic#findAllUsers()
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<User> users = adminLogic.findAllUsers();
        request.getSession().setAttribute("users", users);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_USERS));

        return router;
    }
}
