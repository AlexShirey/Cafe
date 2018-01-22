package com.shirey.cafe.logic;

import com.shirey.cafe.dao.DishDAO;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DishLogic {

    private DishDAO dishDAO = new DishDAO();

    //ok+
    public List<Dish> findDishesInMenu() throws LogicException {

        try {
            return dishDAO.findDishesInMenu();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


    //ok+
    public Map<Dish, Integer> findDishesInOrder(int orderId) throws LogicException {

        try {
            return dishDAO.findDishesInOrder(orderId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


    //ok+
    public Dish findDishById(int dishToEditId) throws LogicException {
        try {
            return dishDAO.findEntityById(dishToEditId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


    //ok+
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
