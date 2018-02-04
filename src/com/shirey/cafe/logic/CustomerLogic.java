package com.shirey.cafe.logic;

import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.entity.PaymentType;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * The {@code UserLogic} class
 * contains methods to provide customer logic
 *
 * @author Alex Shirey
 */

public class CustomerLogic {

    private UserDAO userDAO = new UserDAO();

    /**
     * Adds amount of money to user balance,
     * updates a database with this new balance value and
     * sets this value to the current {@code User} object after the update.
     *
     * @param user   a {@code User} object which balance to update
     * @param amount a amount to add to the user's balance
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
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

    /**
     * Checks if user balance is not less then cart price
     *
     * @param user      a {@code User} to check
     * @param cartPrice a value to compare to
     * @return a {@code true} if the user balance is not less then cart price, {@code false} otherwise
     */
    public boolean checkBalance(User user, BigDecimal cartPrice) {

        BigDecimal balance = user.getAccount().getBalance();
        return balance.compareTo(cartPrice) >= 0;
    }

    /**
     * Checks if user loyalty points is not less then cart price
     *
     * @param user      a {@code User} to check
     * @param cartPrice a value to compare to
     * @return a {@code true} if the user loyalty points are not less then cart price, {@code false} otherwise
     */
    public boolean checkLoyaltyPoints(User user, BigDecimal cartPrice) {

        BigDecimal loyaltyPoints = user.getAccount().getLoyaltyPoints();
        return loyaltyPoints.compareTo(cartPrice) >= 0;
    }

    /**
     * Defines and returns a {@code BigDecimal} value -
     * percent of all price.
     * Percent depends on payment type.
     *
     * @param price       an init value from which a percent will be taken
     * @param paymentType a payment type to get a percent from
     * @return a calculated {@code BigDecimal} value
     */
    public BigDecimal definePointsAmount(BigDecimal price, PaymentType paymentType) {

        return price.multiply(new BigDecimal(paymentType.getLoyaltyPointPercent())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Adds this dish and its quantity to the user's shopping cart.
     * If this dish is already presented in the cart, then quantity of
     * this dish summed up with previous quantity.
     *
     * @param cart      a user's shopping cart
     * @param dishToAdd a dish to add to the cart
     * @param quantity  current dish quantity
     */
    public void addDishToCart(Map<Dish, Integer> cart, Dish dishToAdd, int quantity) {

        if (!cart.containsKey(dishToAdd)) {
            cart.put(dishToAdd, quantity);
        } else {
            int prevQuantity = cart.get(dishToAdd);
            cart.put(dishToAdd, prevQuantity + quantity);
        }
    }

    /**
     * Removes this dish from the cart.
     *
     * @param cart           a user's shopping cart
     * @param dishToRemoveId id of the dish to remove     *
     */
    public void removeDishFromCart(Map<Dish, Integer> cart, int dishToRemoveId) {
        Dish dishToRemove = cart.keySet().stream().filter(dish -> dishToRemoveId == dish.getDishId()).findAny().orElse(null);
        cart.remove(dishToRemove);
    }

    /**
     * Defines cart price using dish price and its quantity.
     *
     * @param cart a user's shopping cart
     * @return a {@code BigDecimal} value that represent total price of all dishes in the cart
     */
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

}

