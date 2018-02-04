package com.shirey.cafe.logic;

import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.manager.ApplicationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * The {@code OrderAutoCancelDaemon} class
 * is a thread that cancels the order if it is not picked up (order status is active)
 * when time to sleep (pick up time - current time + additional time) is gone.
 * If user has 3 cancelled orders, this thread also bans this user.
 *
 * @author Alex Shirey
 */


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

    /**
     * Current thread sleeps a defined time,
     * if the order is still active, cancels the order and
     * bans user if he has 3 cancelled orders.
     *
     * @throws RuntimeException in case of InterruptedException or LogicException
     */
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


    /**
     * Defines additional time to sleep after pick up date comes.
     * Additional time is a value in a properties file.
     *
     * @return a long time in millis
     * @throws ClassCastException    if the object found for the given key is not a string
     * @throws RuntimeException      if no resource bundle for the specified base name can be found or
     *                               if no object for the given key can be found
     * @throws NumberFormatException if the string does not contain a
     *                               parsable {@code long}.
     */
    public static long getAdditionalTimeBeforeCancel() {

        if (additionalTimeBeforeCancel == 0) {
            additionalTimeBeforeCancel = TimeUnit.MINUTES.toMillis(Long.parseLong(ApplicationManager.getProperty("app.additionalTimeInMinutesBeforeAutoCancel")));
        }
        return additionalTimeBeforeCancel;
    }

}
