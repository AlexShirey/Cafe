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
 * The {@code ChangeUserNamesCommand} class
 * is a command to change current user names.
 *
 * @author Alex Shirey
 */

public class ChangeUserNamesCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_FIRST_NAME = "first_name";
    private static final String PARAM_LAST_NAME = "last_name";
    private UserLogic userLogic;

    public ChangeUserNamesCommand(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    /**
     * Gets user first and last names values which he wants to change from the request.
     * Validates input values, if input data is not valid, returns router to the same page with message about invalid input data.
     * Otherwise, edits user values (updates database) and returns router to the same page with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validateName(String)
     * @see UserLogic#changeNames(User, String, String)
     */
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
