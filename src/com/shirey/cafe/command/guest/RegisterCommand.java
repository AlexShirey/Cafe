package com.shirey.cafe.command.guest;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.GuestLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

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

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String phone = request.getParameter(PARAM_PHONE);

        // если ввел невалидные данные, то обратно на форму
        if (!InputDataValidator.validateRegistrationForm(login, password, firstName, lastName, phone)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_REGISTRATION));
        }

        if (guestLogic.isLoginFree(login)) {
            User user = guestLogic.registerUser(login, password, firstName, lastName, phone);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("isLogin", true);
            request.getSession().setAttribute("role", user.getRole().name().toLowerCase());
            return redirectToHomePage();
        } else {
            request.setAttribute("messageLoginAlreadyExist", true);
            return refreshForward(PageManager.getProperty(PAGE_REGISTRATION));
        }
    }
}
