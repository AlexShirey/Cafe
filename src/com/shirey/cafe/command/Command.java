package com.shirey.cafe.command;

import com.shirey.cafe.controller.Router;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;


public interface Command {

    String PAGE_INDEX = "page.index";

    Router execute(HttpServletRequest request) throws LogicException;

    default Router refreshForward(String page) {
        Router router = new Router();
        router.setPage(page);
        return router;
    }

    default Router refreshRedirect(String page) {
        Router router = new Router();
        router.setPage(page);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }

    default Router redirectToHomePage() {
        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_INDEX));
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }

}
