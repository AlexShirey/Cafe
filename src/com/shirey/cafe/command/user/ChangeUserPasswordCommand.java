package com.shirey.cafe.command.user;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code ChangeUserPasswordCommand} class
 * is a command to change current user password.
 *
 * @author Alex Shirey
 */

public class ChangeUserPasswordCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_PASSWORD = "password";
    private UserLogic userLogic;

    public ChangeUserPasswordCommand(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    /**
     * Gets user password which he wants to change from the request.
     * Validates input value, if input data is not valid, returns router to the same page with message about invalid input data.
     * Otherwise, edits user value (updates database) and returns router to the same page with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validatePassword(String)
     * @see UserLogic#changePassword(User, String)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String password = request.getParameter(PARAM_PASSWORD);

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
