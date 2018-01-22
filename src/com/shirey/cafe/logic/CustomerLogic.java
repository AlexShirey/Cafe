package com.shirey.cafe.logic;

import com.shirey.cafe.dao.DishDAO;
import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CustomerLogic {

    private UserDAO userDAO = new UserDAO();

    //ok+
    public void addMoney(User user, String amount) throws LogicException {

        BigDecimal balance = user.getAccount().getBalance();
        balance = balance.add(new BigDecimal(amount));
        try {
            userDAO.updateBalance(user.getUserId(), balance);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.getAccount().setBalance(balance);
    }

    //ok+
    public boolean checkBalance(User user, BigDecimal cartPrice) {

        BigDecimal balance = user.getAccount().getBalance();
        return balance.compareTo(cartPrice) >= 0;
    }

    //ok+
    public boolean checkLoyaltyPoints(User user, BigDecimal cartPrice) {

        BigDecimal loyaltyPoints = user.getAccount().getLoyaltyPoints();
        return loyaltyPoints.compareTo(cartPrice) >= 0;
    }

    //ok+
    public BigDecimal definePointsAmount(BigDecimal price, PaymentType paymentType) {

        return price.multiply(new BigDecimal(paymentType.getLoyaltyPointPercent())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    //ok+
    public void addDishToCart(Map<Dish, Integer> cart, Dish dishToAdd, String quantity) {

        if (!cart.containsKey(dishToAdd)) {
            cart.put(dishToAdd, Integer.parseInt(quantity));
        } else {
            int prevQuantity = cart.get(dishToAdd);
            cart.put(dishToAdd, prevQuantity + Integer.parseInt(quantity));
        }
    }

    //ok+
    public void removeDishFromCart(Map<Dish, Integer> cart, int dishToRemoveId) {
        Dish dishToRemove = cart.keySet().stream().filter(dish -> dishToRemoveId == dish.getDishId()).findAny().orElse(null);
        cart.remove(dishToRemove);
    }

    //ok+
    public BigDecimal defineCartPrice(Map<Dish, Integer> cart) {

        BigDecimal cartPrice = new BigDecimal(0);
        Set<Map.Entry<Dish, Integer>> entries = cart.entrySet();

        for (Map.Entry<Dish, Integer> entry : entries) {
            BigDecimal price = entry.getKey().getPrice();
            BigDecimal quantity = new BigDecimal(entry.getValue());
            cartPrice = cartPrice.add(price.multiply(quantity));
        }

        return cartPrice;
    }

    //ok+
    public List<User> findCustomersWithReview() throws LogicException {

        try {
            return userDAO.findUsersWithReview();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

}

