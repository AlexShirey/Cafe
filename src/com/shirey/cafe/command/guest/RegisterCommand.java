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
 * The {@code RegisterCommand} class
 * is a command to register new user.
 *
 * @author Alex Shirey
 */

public class RegisterCommand implements Command {

    private static final String PAGE_REGISTRATION = "page.registration";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private static final String PARAM_PHONE = "phone";
    private GuestLogic guestLogic;

    public RegisterCommand(GuestLogic guestLogic) {
        this.guestLogic = guestLogic;
    }

    /**
     * Gets login, password, firstName, lastName and phone values from the request.
     * Validates this values, if input data is not valid, returns router to the same page with message about incorrect input data.
     * Checks, if the user with this login already exists, returns router to the same page with message about login already exists.
     * Otherwise, register new user (creates entity and updates database),
     * sets sessions attributes for current user and
     * returns router to the main page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validateRegistrationForm(String, String, String, String, String)
     * @see GuestLogic#isLoginFree(String)
     * @see GuestLogic#registerUser(String, String, String, String, String)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String phone = request.getParameter(PARAM_PHONE);

        if (!InputDataValidator.validateRegistrationForm(login, password, firstName, lastName, phone)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_REGISTRATION));
        }

        if (!guestLogic.isLoginFree(login)) {
            request.setAttribute("messageLoginAlreadyExist", true);
            return refreshForward(PageManager.getProperty(PAGE_REGISTRATION));
        }

        User user = guestLogic.registerUser(login, password, firstName, lastName, phone);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("isLogin", true);
        request.getSession().setAttribute("role", user.getRole().name().toLowerCase());
        return redirectToHomePage();

    }
}
