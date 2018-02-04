package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;

import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.ApplicationManager;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The {@code ShowPlaceOrderPageCommand} class
 * is a command to show place order page.
 *
 * @author Alex Shirey
 */

public class ShowPlaceOrderPageCommand implements Command {

    private static final String PAGE_PLACE_ORDER = "page.placeOrder";
    private CustomerLogic customerLogic;

    public ShowPlaceOrderPageCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    /**
     * Defines and sets as session attributes values of
     * loyalty points that will be added if order is confirmed,
     * (the amount depends on the payment type),
     * defines and sets as session attributes min and max pick up time (when the order can be picked up) values
     * (this values are set in the properties file).
     * <p>
     * Returns router to the place order page.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see CustomerLogic#definePointsAmount(BigDecimal, PaymentType)
     */
    @Override
    public Router execute(HttpServletRequest request) {

        BigDecimal cartPrice = (BigDecimal) request.getSession().getAttribute("cartPrice");

        BigDecimal pointsForAccount = customerLogic.definePointsAmount(cartPrice, PaymentType.ACCOUNT);
        BigDecimal pointsForCash = customerLogic.definePointsAmount(cartPrice, PaymentType.CASH);

        request.getSession().setAttribute("pointsForAccount", pointsForAccount);
        request.getSession().setAttribute("pointsForCash", pointsForCash);

        LocalDateTime now = LocalDateTime.now();
        now = now.truncatedTo(ChronoUnit.MINUTES);

        LocalDateTime minPickUpTime = now.plusMinutes(Long.parseLong(ApplicationManager.getProperty("app.orderNotEarlierTimeInMinutes")));
        LocalDateTime maxPickUpTime = now.plusHours(Long.parseLong(ApplicationManager.getProperty("app.orderNotLaterTimeInHours")));

        request.getSession().setAttribute("minPickUpTime", minPickUpTime);
        request.getSession().setAttribute("maxPickUpTimeTime", maxPickUpTime);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_PLACE_ORDER));

        return router;
    }
}
