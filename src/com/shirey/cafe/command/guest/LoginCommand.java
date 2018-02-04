package com.shirey.cafe.command.guest;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.GuestLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code LoginCommand} class
 * is a command to log in (authorise) a user.
 *
 * @author Alex Shirey
 */

public class LoginCommand implements Command {

    private static final String PAGE_LOGIN = "page.login";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private GuestLogic guestLogic;

    public LoginCommand(GuestLogic guestLogic) {
        this.guestLogic = guestLogic;
    }

    /**
     * Gets login and password values from the request.
     * Validates this values, if input data is not valid, or no such user is presented in the database (user is null),
     * returns router to the same page with message about incorrect login or password.
     * Otherwise, finds the user by this values and sets sessions attributes and
     * returns router to the main page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validateLoginForm(String, String)
     * @see GuestLogic#findUserByLoginAndPass(String, String)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (!InputDataValidator.validateLoginForm(login, password)) {
            request.setAttribute("messageIncorrectLoginOrPass", true);
            return refreshForward(PageManager.getProperty(PAGE_LOGIN));
        }

        User user = guestLogic.findUserByLoginAndPass(login, password);

        if (user == null) {
            request.setAttribute("messageIncorrectLoginOrPass", true);
            return refreshForward(PageManager.getProperty(PAGE_LOGIN));
        }

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("isLogin", true);
        request.getSession().setAttribute("role", user.getRole().name().toLowerCase());
        return redirectToHomePage();

    }
}
