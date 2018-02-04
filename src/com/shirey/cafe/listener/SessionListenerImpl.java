package com.shirey.cafe.listener;

import com.shirey.cafe.entity.Dish;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code SessionListenerImpl} class
 * is a session listener
 *
 * @author Alex Shirey
 */

@WebListener
public class SessionListenerImpl implements HttpSessionListener {

    private static final String ATTR_LOCALE = "locale";
    private static final String ATTR_DATE_FORMAT_STYLE = "dateFormatStyle";
    private static final String ATTR_CART = "cart";


    /**
     * Sets default locale, date format style and a shopping cart
     * as session attributes when session is created
     *
     * @param se {@code HttpSessionEvent}
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        se.getSession().setAttribute(ATTR_LOCALE, "en_US");
        se.getSession().setAttribute(ATTR_DATE_FORMAT_STYLE, "MEDIUM");

        Map<Dish, Integer> shoppingCart = new LinkedHashMap<>();
        se.getSession().setAttribute(ATTR_CART, shoppingCart);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
