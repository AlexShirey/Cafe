package com.shirey.cafe.logic;

import com.shirey.cafe.dao.DishDAO;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code DishLogic} class
 * contains methods to provide logic operations with {@code Dish} objects.
 *
 * @author Alex Shirey
 */

public class DishLogic {

    private DishDAO dishDAO = new DishDAO();

    /**
     * Gets all dishes presented in the menu from a database.
     *
     * @return a list contains {@code Dish}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<Dish> findDishesInMenu() throws LogicException {

        try {
            return dishDAO.findDishesInMenu();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets a map contains all dishes presented in the order and their quantity from a database.
     *
     * @param orderId a order id
     * @return a map contains {@code Dish} and their quantity in the order, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public Map<Dish, Integer> findDishesInOrder(int orderId) throws LogicException {

        try {
            return dishDAO.findDishesInOrder(orderId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets (after finding and creating) a {@code Dish} object from a database using dishId.
     *
     * @param dishId a dish id to find and create the {@code Dish} object
     * @return a founded and created {@code Dish} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public Dish findDishById(int dishId) throws LogicException {
        try {
            return dishDAO.findEntityById(dishId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Checks, if the dishes in the menu are not like the same dishes in the customer's shopping cart
     * using equals() method. If the dish is not equal, this dish is removed from the shopping cart.
     *
     * @param cart a cart to check
     * @return a {@code true} if at least one dish in the menu is not equal to the same dish in the cart, {@code false} otherwise
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public boolean isMenuChanged(Map<Dish, Integer> cart) throws LogicException {

        boolean flag = false;
        Set<Dish> dishesInCart = cart.keySet();
        Iterator<Dish> iterator = dishesInCart.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            try {
                if (!dish.equals(dishDAO.findEntityById(dish.getDishId()))) {
                    iterator.remove();
                    flag = true;
                }
            } catch (DAOException e) {
                throw new LogicException(e);
            }
        }
        return flag;
    }
}
