package com.shirey.cafe.command.user;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserPasswordCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_PASSWORD = "password";
    private UserLogic userLogic;

    public ChangeUserPasswordCommand(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String password = request.getParameter(PARAM_PASSWORD);

        // если ввел невалидные данные, то обратно на форму
        if (!InputDataValidator.validatePassword(password)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_PROFILE));
        }

        User user = (User) request.getSession().getAttribute("user");
        userLogic.changePassword(user, password);

        request.getSession().setAttribute("messageProfileChanged", true);

        return refreshRedirect(PageManager.getProperty(PAGE_PROFILE));
    }
}
