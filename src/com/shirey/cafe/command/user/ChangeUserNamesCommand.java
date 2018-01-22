package com.shirey.cafe.command.user;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserNamesCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private UserLogic userLogic;

    public ChangeUserNamesCommand(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);

        if (!InputDataValidator.validateName(firstName) || !InputDataValidator.validateName(lastName)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_PROFILE));
        }

        User user = (User) request.getSession().getAttribute("user");
        userLogic.changeNames(user, firstName, lastName);

        request.getSession().setAttribute("messageProfileChanged", true);

        return refreshRedirect(PageManager.getProperty(PAGE_PROFILE));
    }
}
