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
 * The {@code ChangeUserPhoneCommand} class
 * is a command to change current user phone.
 *
 * @author Alex Shirey
 */

public class ChangeUserPhoneCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_PHONE = "phone";
    private UserLogic userLogic;

    public ChangeUserPhoneCommand(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    /**
     * Gets user phone which he wants to change from the request.
     * Validates input value, if input data is not valid, returns router to the same page with message about invalid input data.
     * Otherwise, edits user value (updates database) and returns router to the same page with success message.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     * @see InputDataValidator#validatePhone(String)
     * @see UserLogic#changePhone(User, String)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String phone = request.getParameter(PARAM_PHONE);

        if (!InputDataValidator.validatePhone(phone)) {
            request.setAttribute("messageInvalidInputData", true);
            return refreshForward(PageManager.getProperty(PAGE_PROFILE));
        }

        User user = (User) request.getSession().getAttribute("user");
        userLogic.changePhone(user, phone);

        request.getSession().setAttribute("messageProfileChanged", true);

        return refreshRedirect(PageManager.getProperty(PAGE_PROFILE));
    }
}
