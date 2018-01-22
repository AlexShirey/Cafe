package com.shirey.cafe.logic;

import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.ApplicationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class OrderAutoCancelDaemon extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(OrderAutoCancelDaemon.class);
    private static long additionalTimeBeforeCancel;
    private OrderLogic orderLogic = new OrderLogic();
    private UserLogic userLogic = new UserLogic();
    private long timeToSleepBeforeCancel;
    private User user;
    private Order order;

    OrderAutoCancelDaemon(User user, Order order) {
        this.user = user;
        this.order = order;
        timeToSleepBeforeCancel = order.getPickUpTime().getTime() - System.currentTimeMillis() + getAdditionalTimeBeforeCancel();
    }

    @Override
    public void run() {

        try {
            TimeUnit.MILLISECONDS.sleep(timeToSleepBeforeCancel);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Interrupted exception while sleeping.", e);
            throw new RuntimeException("Interrupted exception while sleeping.", e);
        }

        try {
            if (orderLogic.isOrderActive(order)) {

                orderLogic.cancelOrder(user, order);

                if (orderLogic.getUserCancelledOrdersAmount(order.getUserId()) == 3) {
                    userLogic.banUser(user);
                }
            }
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, "LogicException while trying to cancel order.", e);
            throw new RuntimeException("LogicException while trying to cancel order.", e);
        }
    }

    public static long getAdditionalTimeBeforeCancel() {
        if (additionalTimeBeforeCancel == 0) {
            additionalTimeBeforeCancel = TimeUnit.MINUTES.toMillis(Long.parseLong(ApplicationManager.getProperty("app.additionalTimeInMinutesBeforeAutoCancel")));
        }
        return additionalTimeBeforeCancel;
    }

}
