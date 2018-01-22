package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;

import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.ApplicationManager;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class PlaceOrderCommand implements Command {

    private static final String PAGE_PLACE_ORDER = "page.placeOrder";
    private CustomerLogic customerLogic;

    public PlaceOrderCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

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
