package com.shirey.cafe.dao;

import com.shirey.cafe.db.ConnectionPool;
import com.shirey.cafe.db.ProxyConnection;
import com.shirey.cafe.entity.Dish;
import com.shirey.cafe.exception.ConnectionException;
import com.shirey.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DishDAO extends AbstractDAO<Integer, Dish> {

    private static final String SQL_INSERT_NEW_DISH =
            "INSERT INTO dish(type_id, name, description, dish_price, in_menu) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_DISH_BY_ID =
            "SELECT dish_id, type_id, name, description, dish_price, in_menu, create_date FROM dish WHERE dish_id=?";

    private static final String SQL_SELECT_All_DISHES =
            "SELECT dish_id, type_id, name, description, dish_price, in_menu, create_date FROM dish";

    private static final String SQL_SELECT_ALL_DISH_TYPES =
            "SELECT type FROM dish_type";

    private static final String SQL_SELECT_DISHES_IN_MENU =
            "SELECT dish_id, type_id, name, description, dish_price, in_menu, create_date FROM dish WHERE in_menu=1";

    private static final String SQL_SELECT_DISHES_IN_ORDER =
            "SELECT dish_id, type_id, name, description, order_has_dish.dish_price, in_menu, create_date, order_has_dish.dish_quantity FROM dish JOIN order_has_dish USING (dish_id) WHERE order_id=?";

    private static final String SQL_UPDATE_DISH =
            "UPDATE dish SET description=?, dish_price=?, in_menu=? WHERE dish_id=?";


    public void create(Dish dish) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_DISH, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, dish.getType().getDishTypeId());
            preparedStatement.setString(2, dish.getName());
            preparedStatement.setString(3, dish.getDescription());
            preparedStatement.setBigDecimal(4, dish.getPrice());
            preparedStatement.setBoolean(5, dish.isInMenu());
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Creating dish failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dish.setDishId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("no auto-generated keys.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    @Override
    public Dish findEntityById(Integer id) throws DAOException {

        Dish dish = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DISH_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                dish = buildDish(rs);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return dish;
    }


    @Override
    public List<Dish> findAll() throws DAOException {

        List<Dish> dishes = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_All_DISHES);
            while (resultSet.next()) {
                Dish dish = buildDish(resultSet);
                dishes.add(dish);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return dishes;
    }

    public List<String> findAllDishTypes() throws DAOException {

        List<String> dishTypes = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_DISH_TYPES);
            while (resultSet.next()) {
                dishTypes.add(resultSet.getString(1));
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return dishTypes;
    }


    public List<Dish> findDishesInMenu() throws DAOException {

        List<Dish> menu = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_DISHES_IN_MENU);
            while (resultSet.next()) {
                Dish dish = buildDish(resultSet);
                menu.add(dish);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return menu;
    }


    public Map<Dish, Integer> findDishesInOrder(int orderId) throws DAOException {

        Map<Dish, Integer> dishes = new HashMap<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_DISHES_IN_ORDER)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dish dish = buildDish(resultSet);
                dishes.put(dish, resultSet.getInt("dish_quantity"));
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return dishes;
    }


    public void updateDish(int dishId, String description, BigDecimal price, boolean inMenu) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DISH)) {
            preparedStatement.setString(1, description);
            preparedStatement.setBigDecimal(2, price);
            preparedStatement.setBoolean(3, inMenu);
            preparedStatement.setInt(4, dishId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating dish failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }


    private Dish buildDish(ResultSet rs) throws SQLException {

        Dish dish = new Dish();
        dish.setDishId(rs.getInt("dish_id"));
        dish.setType(rs.getInt("type_id"));
        dish.setName(rs.getString("name"));
        dish.setDescription(rs.getString("description"));
        dish.setPrice(rs.getBigDecimal("dish_price"));
        dish.setInMenu(rs.getBoolean("in_menu"));
        dish.setCreateDate(rs.getTimestamp("create_date"));

        return dish;
    }

}
