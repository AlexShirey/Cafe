package com.shirey.cafe.filter;

import com.shirey.cafe.manager.PageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code UserPackageSecurityFilter} class
 * is an implementation of {@code Filter} interface.
 * <p>
 * Filters the jsp user package -
 * forwards request and response to the login page if a user role is null (not logged in).
 *
 * @author Alex Shirey
 */

@WebFilter(urlPatterns = "/jsp/user/*")
public class UserPackageSecurityFilter implements Filter {

    private static final String PAGE_LOGIN = "page.login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String role = (String) httpServletRequest.getSession().getAttribute("role");
        if (role == null) {
            RequestDispatcher dispatcher = httpServletRequest.getServletContext().getRequestDispatcher(PageManager.getProperty(PAGE_LOGIN));
            dispatcher.forward(httpServletRequest, httpServletResponse);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
