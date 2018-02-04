package com.shirey.cafe.logic;

import com.shirey.cafe.dao.DishDAO;
import com.shirey.cafe.dao.OrderDAO;
import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.*;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code AdminLogic} class
 * contains methods to provide admin logic
 *
 * @author Alex Shirey
 */

public class AdminLogic {

    private UserDAO userDAO = new UserDAO();
    private DishDAO dishDAO = new DishDAO();
    private OrderDAO orderDAO = new OrderDAO();

    /**
     * Gets all users from a database.
     *
     * @return a list contains {@code User}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<User> findAllUsers() throws LogicException {

        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets all dishes from a database.
     *
     * @return a list contains {@code Dish}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<Dish> findAllDishes() throws LogicException {

        try {
            return dishDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets all orders from a database in reverse order.
     *
     * @return a list contains {@code Order}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<Order> findAllOrders() throws LogicException {

        try {
            return orderDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets all user roles from a database.
     *
     * @return a list contains user role values as {@code String}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<String> findAllUserRoles() throws LogicException {

        try {
            return userDAO.findAllUserRoles();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets all dish types from a database.
     *
     * @return a list contains dish type values as {@code String}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<String> findAllDishTypes() throws LogicException {
        try {
            return dishDAO.findAllDishTypes();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Edits {@code User} object -
     * updates database values - loyalty points, active status and role id (role status)
     * for user that should be edited.
     * After the update, updated values are set to this {@code User} object
     *
     * @param userToEdit    a {@code User} that should be edited
     * @param loyaltyPoints a new loyalty points value
     * @param active        a new active value (true if user is not banned, false otherwise)
     * @param role          a new user role value
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void editUser(User userToEdit, BigDecimal loyaltyPoints, boolean active, UserRole role) throws LogicException {

        int roleId = role.getUserRoleId();

        try {
            userDAO.updateUser(userToEdit.getUserId(), loyaltyPoints, active, roleId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        userToEdit.getAccount().setLoyaltyPoints(loyaltyPoints);
        userToEdit.setActive(active);
        userToEdit.setRole(roleId);
    }

    /**
     * Edits {@code Dish} object -
     * updates database values - description, price and inMenu status
     * for dish that should be edited.
     * After the update, updated values are set to this {@code Dish} object
     *
     * @param dishToEdit  a {@code Dish} that should be edited
     * @param description a new description value
     * @param price       a new price value
     * @param inMenu      a new inMenu value (in  menu - true, otherwise - false)
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void editDish(Dish dishToEdit, String description, BigDecimal price, boolean inMenu) throws LogicException {

        try {
            dishDAO.updateDish(dishToEdit.getDishId(), description, price, inMenu);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        dishToEdit.setDescription(description);
        dishToEdit.setPrice(price);
        dishToEdit.setInMenu(inMenu);
    }

    /**
     * Creates {@code Dish} object and
     * updates database with a new row that represents this object.
     * If the update is successful, returns {@code Dish} object that is built from updated database
     *
     * @param type        a dish type
     * @param name        a dish name
     * @param description a dish description
     * @param price       a dish price
     * @param inMenu      a dish inMenu (in  menu - true, otherwise - false)
     * @return a {@code Dish} object that is build from updated database
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public Dish addDish(DishType type, String name, String description, BigDecimal price, boolean inMenu) throws LogicException {

        Dish dish = new Dish(type, name, description, price, inMenu);
        try {
            dishDAO.create(dish);
            return dishDAO.findEntityById(dish.getDishId());
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Checks, if a user has at list one active order
     *
     * @param userToEdit a {@code User} that should be checked
     * @return a {@code true} if the user has at list one active order, {@code false} otherwise
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public boolean hasCustomerActiveOrders(User userToEdit) throws LogicException {

        try {
            return !orderDAO.findActiveOrdersByUserId(userToEdit.getUserId()).isEmpty();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Updates a database with null values of order rating and review,
     * sets this values to the {@code Order} object after the update
     *
     * @param order a {@code Order} object which rating and review should be removed (set to null)
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void removeReview(Order order) throws LogicException {

        try {
            orderDAO.updateOrderReview(order.getOrderId(), null, null);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        order.setRating(0);
        order.setReview(null);
    }

}
