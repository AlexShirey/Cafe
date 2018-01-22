package com.shirey.cafe.command.guest;


import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.GuestLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    private static final String PAGE_LOGIN = "page.login";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private GuestLogic guestLogic;

    public LoginCommand(GuestLogic guestLogic) {
        this.guestLogic = guestLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        // если ввел невалидные данные, то обратно на форму
        if (!InputDataValidator.validateLoginForm(login, password)) {
            request.setAttribute("messageIncorrectLoginOrPass", true);
            return refreshForward(PageManager.getProperty(PAGE_LOGIN));
        }

        // если такой юзер есть в базе данных, то заходим, если нет - то message и редирект (что бы по F5 не заходил в базу данных еще раз)
        User user = guestLogic.findUserByLoginAndPass(login, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("isLogin", true);
            request.getSession().setAttribute("role", user.getRole().name().toLowerCase());
            return redirectToHomePage();
        } else {
            request.setAttribute("messageIncorrectLoginOrPass", true);
            return refreshForward(PageManager.getProperty(PAGE_LOGIN));
        }
    }
}
