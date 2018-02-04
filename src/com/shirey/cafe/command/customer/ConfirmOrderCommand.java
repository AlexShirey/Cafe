package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.logic.OrderAutoCancelDaemon;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.util.LocalDateTimeParser;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code ConfirmOrderCommand} class
 * is a command to place new order.
 *
 * @author Alex Shirey
 */

public class ConfirmOrderCommand implements Command {

    private static final String PAGE_CART = "page.cart";
    private static final String PAGE_CONFIRMED_ORDER = "page.confirmedOrder";
    private static final String PARAM_PAYMENT_TYPE = "paymentType";
    private static final String PARAM_PICKUP_TIME = "pickUpTime";
    private CustomerLogic customerLogic;
    private OrderLogic orderLogic;
    private DishLogic dishLogic;

    public ConfirmOrderCommand(CustomerLogic customerLogic, OrderLogic orderLogic, DishLogic dishLogic) {
        this.customerLogic = customerLogic;
        this.orderLogic = orderLogic;
        this.dishLogic = dishLogic;
    }

    /**
     * Gets payment type and pick up time values from request,
     * checks, if the cart is empty (if customer removed dishes in a new browser tab),
     * returns router to the shopping cart page.
     * <p>
     * Then checks, if the menu was changed during customer was placing this order
     * (admin can change dishes that customer has chosen), returns router to the cart page with message.
     * <p>
     * Then checks, if the customer's balance and loyalty points amount is less then order price,
     * returns router with message about it.
     * <p>
     * Places new order (updates database) and clears the shopping cart,
     * returns router to the confirmed order page with success message and
     * message that order will be auto cancelled if it is not picked up at time.
     *
     * @param request an {@link HttpServletRequest} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see DishLogic#isMenuChanged(Map)
     * @see CustomerLogic#checkBalance(User, BigDecimal)
     * @see CustomerLogic#checkLoyaltyPoints(User, BigDecimal)
     * @see OrderLogic#makeOrder(User, PaymentType, Date, BigDecimal, Map)
     */
    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String stringPaymentType = request.getParameter(PARAM_PAYMENT_TYPE);
        String stringPickUpTime = request.getParameter(PARAM_PICKUP_TIME);

        Map<Dish, Integer> cart = (LinkedHashMap<Dish, Integer>) request.getSession().getAttribute("cart");
        BigDecimal cartPrice = (BigDecimal) request.getSession().getAttribute("cartPrice");
        User user = (User) request.getSession().getAttribute("user");

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_CART));

        if (cart.isEmpty()) {
            return router;
        }

        router.setRoute(Router.RouteType.REDIRECT);

        if (dishLogic.isMenuChanged(cart)) {
            cartPrice = customerLogic.defineCartPrice(cart);
            request.getSession().setAttribute("cartPrice", cartPrice);
            request.getSession().setAttribute("messageMenuChanged", true);
            return router;
        }

        PaymentType paymentType;
        try {
            paymentType = PaymentType.valueOf(stringPaymentType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new LogicException("no such payment type", e);
        }

        Date pickUpTime;
        try {
            pickUpTime = LocalDateTimeParser.parseLdtStringToDate(stringPickUpTime);
        } catch (ParseException e) {
            throw new LogicException("can't parseLdtStringToDate input string - localeDateTime to Date", e);
        }

        router.setPage(PageManager.getProperty(PAGE_CONFIRMED_ORDER));

        switch (paymentType) {
            case ACCOUNT:
                if (!customerLogic.checkBalance(user, cartPrice)) {
                    request.getSession().setAttribute("messageNotEnoughMoney", true);
                    return router;
                }
                orderLogic.makeOrder(user, paymentType, pickUpTime, cartPrice, cart);
                request.getSession().setAttribute("messageMoneyWithdrawn", true);
                break;
            case CASH:
                orderLogic.makeOrder(user, paymentType, pickUpTime, cartPrice, cart);
                request.getSession().setAttribute("messagePayOnReceiving", true);
                break;
            case LOYALTY_POINTS:
                if (!customerLogic.checkLoyaltyPoints(user, cartPrice)) {
                    request.getSession().setAttribute("messageNotEnoughLoyaltyPoints", true);
                    return router;
                }
                orderLogic.makeOrder(user, paymentType, pickUpTime, cartPrice, cart);
                request.getSession().setAttribute("messageLoyaltyPointsTaken", true);
        }

        cart.clear();

        request.getSession().setAttribute("messageOrderConfirmed", true);

        Date autoCancelDateTime = new Date(pickUpTime.getTime() + OrderAutoCancelDaemon.getAdditionalTimeBeforeCancel());
        request.getSession().setAttribute("autoCancelDateTime", autoCancelDateTime);

        return router;

    }
}
