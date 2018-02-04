package com.shirey.cafe.tag;

import com.shirey.cafe.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The {@code UserInfoCustomTag} class
 * is a custom tag that shows user info - user name, role.
 * If a user is not logged in, the tag shows this information.
 * Supports two languages, en and ru (locale is taken from user session).
 * <p>
 * Output is a {@code String}
 *
 * @author Alex Shirey
 */

public class UserInfoCustomTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        User user = (User) pageContext.getSession().getAttribute("user");
        String locale = (String) pageContext.getSession().getAttribute("locale");
        String loggedFirst;
        String loggedSecond;
        String notLoggedIn;

        String info;

        switch (locale) {
            case ("ru_RU"):
                loggedFirst = "Вы авторизированы как ";
                loggedSecond = ", Ваше имя ";
                notLoggedIn = "Вы не авторизированы, гость. Пожалуйста, войдите.";
                break;
            default:
                loggedFirst = "You logged in as ";
                loggedSecond = ", your name is ";
                notLoggedIn = "You are not logged in, guest. Please, log in.";
        }

        if (user != null) {
            info = loggedFirst + user.getRole().name().toLowerCase() + loggedSecond + user.getFirstName();
        } else {
            info = notLoggedIn;
        }

        JspWriter out = pageContext.getOut();
        try {
            out.write(info);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

}
