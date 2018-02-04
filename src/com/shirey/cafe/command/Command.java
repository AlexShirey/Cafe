package com.shirey.cafe.command;

import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code Command} interface
 * should be implemented by all Command classes in this project.
 *
 * @author Alex Shirey
 */

public interface Command {

    String PAGE_INDEX = "page.index";

    /**
     * Executes the request.
     * Should have own implementation for each command.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    Router execute(HttpServletRequest request) throws LogicException;

    /**
     * Creates a new router object and sets its page.
     * Uses default RouteType.FORWARD
     *
     * @param page a page where request and response should be forwarded.
     * @return a {@code Router} object
     */
    default Router refreshForward(String page) {
        Router router = new Router();
        router.setPage(page);
        return router;
    }

    /**
     * Creates a new router object and sets its page.
     * Sets route type to RouteType.REDIRECT
     *
     * @param page a page where response should be redirected.
     * @return a {@code Router} object
     */
    default Router refreshRedirect(String page) {
        Router router = new Router();
        router.setPage(page);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }

    /**
     * Creates a new router object and sets its page to PAGE_INDEX.
     * Sets route type to RouteType.REDIRECT
     *
     * @return a {@code Router} object
     */
    default Router redirectToHomePage() {
        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_INDEX));
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }

}
