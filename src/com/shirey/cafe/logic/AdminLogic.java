package com.shirey.cafe.logic;

import com.shirey.cafe.dao.DishDAO;
import com.shirey.cafe.dao.OrderDAO;
import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.*;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.math.BigDecimal;
import java.util.List;

public class AdminLogic {

    private UserDAO userDAO = new UserDAO();
    private DishDAO dishDAO = new DishDAO();
    private OrderDAO orderDAO = new OrderDAO();

    //ok+
    public List<User> findAllUsers() throws LogicException {

        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public List<Dish> findAllDishes() throws LogicException {

        try {
            return dishDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public List<Order> findAllOrders() throws LogicException {

        try {
            return orderDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok
    public List<String> findAllUserRoles() throws LogicException {

        try {
            return userDAO.findAllUserRoles();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok
    public List<String> findAllDishTypes() throws LogicException {
        try {
            return dishDAO.findAllDishTypes();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


    //ok+
    public void editUser(User userToEdit, String points, String activeValue, String role) throws LogicException {

        BigDecimal loyaltyPoints = new BigDecimal(points);
        boolean active = Boolean.valueOf(activeValue);
        int roleId = UserRole.valueOf(role.toUpperCase()).getUserRoleId();

        try {
            userDAO.updateUser(userToEdit.getUserId(), loyaltyPoints, active, roleId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        userToEdit.getAccount().setLoyaltyPoints(loyaltyPoints);
        userToEdit.setActive(active);
        userToEdit.setRole(roleId);
    }

    //ok+
    public void editDish(Dish dishToEdit, String description, String priceString, String inMenuString) throws LogicException {

        BigDecimal price = new BigDecimal(priceString);
        boolean inMenu = Boolean.valueOf(inMenuString);

        try {
            dishDAO.updateDish(dishToEdit.getDishId(), description, price, inMenu);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        dishToEdit.setDescription(description);
        dishToEdit.setPrice(price);
        dishToEdit.setInMenu(inMenu);
    }

    //ok+
    public Dish addDish(String type, String name, String description, String price, String inMenu) throws LogicException {

        Dish dish = new Dish(DishType.valueOf(type.toUpperCase()), name, description, new BigDecimal(price), Boolean.valueOf(inMenu));
        try {
            dishDAO.create(dish);
            return dishDAO.findEntityById(dish.getDishId());
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public boolean HasCustomerActiveOrders(User userToEdit) throws LogicException {

        try {
            return !orderDAO.findActiveOrdersByUserId(userToEdit.getUserId()).isEmpty();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
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
