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

public class OrderLogic {

    private CustomerLogic customerLogic = new CustomerLogic();
    private OrderDAO orderDAO = new OrderDAO();

    //ok+
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

    //ok+
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

    //ok+
    public void pickUpOrder(Order order) throws LogicException {

        Order.Status status = Order.Status.FINISHED;

        try {
            orderDAO.updateOrder(order, true, status);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        order.setPaid(true);
        order.setStatus(status);
    }

    //ok+
    public boolean isOrderActive(Order order) throws LogicException {

        try {
            order = orderDAO.findEntityById(order.getOrderId());
            return order.getStatus() == Order.Status.ACTIVE;
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public int getUserCancelledOrdersAmount(int userId) throws LogicException {

        try {
            return orderDAO.findCancelledOrdersByUserId(userId).size();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public List<Order> findUserOrders(int userId) throws LogicException {

        try {
            return orderDAO.findOrdersByUserId(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public List<Order> findOrdersWithReview() throws LogicException {

        try {
            return orderDAO.findOrdersWithReview();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public void leaveFeedback(int orderId, int rating, String review) throws LogicException {

        try {
            orderDAO.updateOrderReview(orderId, rating, review);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

}



