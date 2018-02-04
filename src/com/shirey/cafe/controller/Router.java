package com.shirey.cafe.controller;

/**
 * The {@code Router} class
 * contains two fields -
 * page and RouteTpe,
 * that are used with a controller to find out where and how
 * a request and response should be processed after the controller.
 *
 * @author Alex Shirey
 */

public class Router {

    public enum RouteType {
        FORWARD, REDIRECT
    }

    private String page;

    /**
     * Sets the default route type to FORWARD
     */
    private RouteType route = RouteType.FORWARD;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        this.route = route;
    }
}
