package com.shirey.cafe.tag;

import com.shirey.cafe.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserInfoCustomTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        User user = (User) pageContext.getSession().getAttribute("user");
        String info;

        if (user != null) {
            info = "You logged in as " + user.getRole().name().toLowerCase() + ", your name is " + user.getFirstName();
        } else {
            info = "You are not logged in, guest. Please, log in";
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
