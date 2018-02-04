package com.shirey.cafe.controller;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.command.CommandFactory;

import com.shirey.cafe.db.ConnectionPool;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.exception.UnsupportedCommandException;
import com.shirey.cafe.manager.PageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code Controller} class
 * is a main HttpServlet for current Web project.
 * Overrides doPost and doGet methods by calling
 * the own method processRequest(request, response).
 *
 * @author Alex Shirey
 */

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closeConnections();
    }

    /**
     * This method is called by doGet and doPost methods.
     * Gets the command from the request, and calls method execute(request) on it that has
     * own implementation for each command.
     * Sets the type how request and response should be processed after this controller or
     * redirect a response to the error page if UnsupportedCommandException or LogicException occurs.
     *
     * @param request  an {@link HttpServletRequest} object that
     *                 contains the request the client has made
     *                 of the servlet
     * @param response an {@link HttpServletResponse} object that
     *                 contains the response the servlet sends
     *                 to the client
     * @throws IOException      if an input or output error is
     *                          detected when the servlet handles
     *                          the request
     * @throws ServletException if the request
     *                          could not be handled
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Router router;
        try {
            Command command = CommandFactory.defineCommand(request.getParameter("command"));
            router = command.execute(request);
        } catch (UnsupportedCommandException | LogicException e) {
            router = new Router();
            router.setPage(PageManager.getProperty("page.error"));
            router.setRoute(Router.RouteType.REDIRECT);
            request.getSession().setAttribute("error", e);
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }

        switch (router.getRoute()) {
            case FORWARD:
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(router.getPage());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + router.getPage());
        }
    }
}