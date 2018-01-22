package com.shirey.cafe.dao;

import com.shirey.cafe.db.ConnectionPool;
import com.shirey.cafe.db.ProxyConnection;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.ConnectionException;
import com.shirey.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDAO extends AbstractDAO<Integer, User> {

    private static final String SQL_INSERT_NEW_USER =
            "INSERT INTO user (email, password, first_name, last_name, phone) VALUES (?, MD5(?), ?, ?, ?);";

    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT user_id, email, password, first_name, last_name, phone, create_date, balance, loyalty_points, active, role_id FROM user WHERE user_id=?;";

    private static final String SQL_SELECT_ALL_USERS =
            "SELECT user_id, email, password, first_name, last_name, phone, create_date, balance, loyalty_points, active, role_id  FROM user";

    private static final String SQL_SELECT_ALL_USER_ROLES =
            "SELECT role FROM user_role";

    private static final String SQL_SELECT_USERS_WITH_REVIEW =
            "SELECT user_id, email, password, first_name, last_name, phone, user.create_date, balance, loyalty_points, active, role_id  FROM user JOIN `order` USING(user_id) WHERE review IS NOT NULL GROUP BY user_id";

    private static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT user_id, email, password, first_name, last_name, phone, create_date, balance, loyalty_points, active, role_id  FROM user WHERE email=?;";

    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASS =
            "SELECT user_id, email, password, first_name, last_name, phone, create_date, balance, loyalty_points, active, role_id  FROM user WHERE email=? AND password=MD5(?);";

    private static final String SQL_UPDATE_USER_BALANCE =
            "UPDATE user SET balance=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_PASSWORD =
            "UPDATE user SET password=MD5(?) WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_NAMES =
            "UPDATE user SET first_name=?, last_name=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_PHONE =
            "UPDATE user SET phone=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_ACTIVE_STATUS =
            "UPDATE user SET active=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER =
            "UPDATE user SET loyalty_points=?, active=?, role_id=? WHERE user_id=?;";

    //ok+
    public void create(User user) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhone());
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("no auto-generated keys.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    //ok+
    @Override
    public User findEntityById(Integer id) throws DAOException {

        User user = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = buildUser(rs);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DAOException {

        List<User> users = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return users;
    }


    public List<String> findAllUserRoles() throws DAOException {

        List<String> userRoles = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER_ROLES);
            while (resultSet.next()) {
                userRoles.add(resultSet.getString(1));
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return userRoles;
    }

    public List<User> findUsersWithReview() throws DAOException {

        List<User> users = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_USERS_WITH_REVIEW);
            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return users;
    }

    //ok+
    public boolean isExist(String login) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    //ok+
    public User findUserByLoginAndPass(String login, String password) throws DAOException {

        User user = null;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASS)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = buildUser(rs);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return user;
    }

    //ok+
    public void updateBalance(int userId, BigDecimal balance) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BALANCE)) {
            preparedStatement.setBigDecimal(1, balance);
            preparedStatement.setInt(2, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating balance failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    //ok+
    public void updatePassword(int userId, String password) throws DAOException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating password failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    //ok+
    public void updateNames(int userId, String firstName, String lastName) throws DAOException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_NAMES)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating names failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    //ok+
    public void updatePhone(int userId, String phone) throws DAOException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PHONE)) {
            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating phone failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    public void updateActiveStatus(int userId, boolean active) throws DAOException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_ACTIVE_STATUS)) {
            preparedStatement.setBoolean(1, active);
            preparedStatement.setInt(2, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating active status failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }

    public void updateUser(int userId, BigDecimal loyaltyPoints, boolean active, int roleId) throws DAOException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setBigDecimal(1, loyaltyPoints);
            preparedStatement.setBoolean(2, active);
            preparedStatement.setInt(3, roleId);
            preparedStatement.setInt(4, userId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
    }


    private User buildUser(ResultSet rs) throws SQLException {

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPhone(rs.getString("phone"));
        user.setCreateDate(rs.getTimestamp("create_date"));
        user.getAccount().setBalance(rs.getBigDecimal("balance"));
        user.getAccount().setLoyaltyPoints(rs.getBigDecimal("loyalty_points"));
        user.setActive(rs.getBoolean("active"));
        user.setRole(rs.getInt("role_id"));

        return user;
    }

}
