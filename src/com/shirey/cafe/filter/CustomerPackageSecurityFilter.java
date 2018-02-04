package com.shirey.cafe.filter;

import com.shirey.cafe.manager.PageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code CustomerPackageSecurityFilter} class
 * is an implementation of {@code Filter} interface,
 * <p>
 * Filters the jsp customer package -
 * forwards request and response to the index page if a user role is not customer
 *
 * @author Alex Shirey
 */

@WebFilter(urlPatterns = "/jsp/customer/*")
public class CustomerPackageSecurityFilter implements Filter {

    private static final String PAGE_INDEX = "page.index";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String role = (String) httpServletRequest.getSession().getAttribute("role");
        if (!"customer".equals(role)) {
            RequestDispatcher dispatcher = httpServletRequest.getServletContext().getRequestDispatcher(PageManager.getProperty(PAGE_INDEX));
            dispatcher.forward(httpServletRequest, httpServletResponse);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
