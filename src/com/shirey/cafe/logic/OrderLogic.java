package com.shirey.cafe.logic;

import com.shirey.cafe.dao.OrderDAO;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The {@code OrderLogic} class
 * contains methods to provide logic operations with {@code Order} objects.
 *
 * @author Alex Shirey
 */

public class OrderLogic {

    private CustomerLogic customerLogic = new CustomerLogic();
    private OrderDAO orderDAO = new OrderDAO();

    /**
     * Makes the order -
     * subtracts the order price from user's balance amount (in the ACCOUNT payment type case),
     * defines loyalty point to subtract and subtracts this value from user's loyalty points amount (in the ACCOUNT and CASH payment type cases) or
     * subtracts the order price from user's loyalty points amount (in the LOYALTY_POINTS payment type case),
     * creates new {@code Order} object and updates a database with this values.
     * After the update, sets updated balance and loyalty points values to the {@code User} object who makes current order.
     * <p>
     * Creates a new daemon {@code Thread} object that cancels the order if it is not picked up.
     *
     * @param user        a {@code User} object to subtract loyalty points
     * @param paymentType a order payment type
     * @param pickUpTime  a date when order is ready to be picked up
     * @param orderPrice  a order price value
     * @param cart        a user cart to get {@code Dish} objects and its quantity
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void makeOrder(User user, PaymentType paymentType, Date pickUpTime, BigDecimal orderPrice, Map<Dish, Integer> cart) throws LogicException {

        BigDecimal balance;
        if (paymentType == PaymentType.ACCOUNT) {
            balance = user.getAccount().getBalance().subtract(orderPrice);
        } else {
            balance = user.getAccount().getBalance();
        }

        BigDecimal loyaltyPoints;
        if (paymentType != PaymentType.LOYALTY_POINTS) {
            BigDecimal pointsAmountToAdd = customerLogic.definePointsAmount(orderPrice, paymentType);
            loyaltyPoints = user.getAccount().getLoyaltyPoints().add(pointsAmountToAdd);
        } else {
            loyaltyPoints = user.getAccount().getLoyaltyPoints().subtract(orderPrice);
        }

        boolean isPaid = paymentType != PaymentType.CASH;

        Order order = new Order(user.getUserId(), paymentType, pickUpTime, orderPrice, isPaid);

        try {
            orderDAO.create(balance, loyaltyPoints, order, cart);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        user.getAccount().setBalance(balance);
        user.getAccount().setLoyaltyPoints(loyaltyPoints);

        OrderAutoCancelDaemon watcher = new OrderAutoCancelDaemon(user, order);
        watcher.setDaemon(true);
        watcher.start();
    }

    /**
     * Cancels the order -
     * defines loyalty points to subtract and subtracts this value from user's loyalty points amount,
     * updates a database with new order status CANCELLED and user loyalty points.
     * After the update, sets this value to the params objects.
     *
     * @param user  a {@code User} object to subtract loyalty points
     * @param order a {@code Order} object to update and set new status
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void cancelOrder(User user, Order order) throws LogicException {

        BigDecimal pointsAmountToSubtract = customerLogic.definePointsAmount(order.getOrderPrice(), order.getPaymentType());
        if (pointsAmountToSubtract.compareTo(user.getAccount().getLoyaltyPoints()) > 0) {
            pointsAmountToSubtract = user.getAccount().getLoyaltyPoints();
        }

        BigDecimal loyaltyPoints = user.getAccount().getLoyaltyPoints().subtract(pointsAmountToSubtract);

        try {
            orderDAO.cancelOrder(loyaltyPoints, order, Order.Status.CANCELLED);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        user.getAccount().setLoyaltyPoints(loyaltyPoints);
        order.setStatus(Order.Status.CANCELLED);
    }

    /**
     * Finishes the order - updates a database with isPaid {@code true} and status 'FINISHED' values,
     * sets this values to the {@code Order} object after the update.
     *
     * @param order a {@code Order} object which should be finished.
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void pickUpOrder(Order order) throws LogicException {

        Order.Status status = Order.Status.FINISHED;

        try {
            orderDAO.updateOrder(order.getOrderId(), true, status);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        order.setPaid(true);
        order.setStatus(status);
    }

    /**
     * Checks, if the order status is active or not
     *
     * @param order an object to check
     * @return a {@code true} if this order status is active, {@code false} otherwise
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public boolean isOrderActive(Order order) throws LogicException {

        try {
            order = orderDAO.findEntityById(order.getOrderId());
            return order.getStatus() == Order.Status.ACTIVE;
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets amount how much cancelled orders user has.
     *
     * @param userId a user id
     * @return amount of user's cancelled orders
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public int getUserCancelledOrdersAmount(int userId) throws LogicException {

        try {
            return orderDAO.findCancelledOrdersByUserId(userId).size();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets all user's orders from a database in reverse order.
     *
     * @param userId a user id
     * @return a list contains {@code Order}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<Order> findUserOrders(int userId) throws LogicException {

        try {
            return orderDAO.findOrdersByUserId(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets orders with review from a database in reverse order.
     *
     * @return a list contains {@code Order}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<Order> findOrdersWithReview() throws LogicException {

        try {
            return orderDAO.findOrdersWithReview();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Updates a database with new values of order rating and review.
     *
     * @param orderId order id
     * @param rating  a rating value to update
     * @param review  a review value to update
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void leaveFeedback(int orderId, int rating, String review) throws LogicException {

        try {
            orderDAO.updateOrderReview(orderId, rating, review);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

}



