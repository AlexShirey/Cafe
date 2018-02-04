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

/**
 * The {@code UserDAO} class
 * provides access to the tables 'user', 'user_role' in the database
 *
 * @author Alex Shirey
 */

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

    private static final String SQL_SELECT_USERS_WITH_ORDER =
            "SELECT user_id, email, password, first_name, last_name, phone, user.create_date, balance, loyalty_points, active, role_id  FROM user JOIN `order` USING(user_id) GROUP BY user_id";

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

    /**
     * Inserts in the table a new row that represents {@code User} object,
     * sets the auto generated id to this {@code User} object
     *
     * @param user a {@code User} object
     * @throws DAOException if a database access error occurs or
     *                      if now rows where inserted
     */
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

    /**
     * Gets a row from the table using user id,
     * builds and returns {@code User} object that represents this id
     *
     * @param id a user id
     * @return a {@code User}, or null if no user id is founded in the table
     * @throws DAOException if a database access error occurs
     */
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

    /**
     * Gets all rows from table 'user' and
     * returns them as a list of {@code User} objects
     *
     * @return a list contains {@code User}, not null
     * @throws DAOException if a database access error occurs
     */
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
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return users;
    }

    /**
     * Gets all rows from table 'user_role' and
     * returns them as a list of {@code String}
     *
     * @return a list contains user role values as {@code String}, not null
     * @throws DAOException if a database access error occurs
     */
    public List<String> findAllUserRoles() throws DAOException {

        List<String> userRoles = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER_ROLES);
            while (resultSet.next()) {
                userRoles.add(resultSet.getString(1));
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return userRoles;
    }

    /**
     * Gets rows from the table 'user' where review is not null (join table 'order'),
     * rows are grouped by user id (only one row for each user),
     * returns them as a list of {@code User} objects
     *
     * @return a list contains {@code User} with reviews, not null
     * @throws DAOException if a database access error occurs
     */
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
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return users;
    }

    /**
     * Gets rows from the table 'user' which have at least one row in the table 'order',
     * rows are grouped by user id (only one row for each user),
     * returns them as a list of {@code User} objects
     *
     * @return a list contains {@code User} with at least one order, not null
     * @throws DAOException if a database access error occurs
     */
    public List<User> findUsersWithOrders() throws DAOException {

        List<User> users = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_USERS_WITH_ORDER);
            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
        } catch (ConnectionException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("SQL exception (query or table failed)", e);
        }
        return users;
    }

    /**
     * Gets a row from the table using user login and password,
     * builds and returns {@code User} object that represents this params
     *
     * @param login    a user login
     * @param password a user password
     * @return a {@code User}, or null if no row is founded in the table with this params
     * @throws DAOException if a database access error occurs
     */
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

    /**
     * Checks if the table 'user' contains a row where 'email' = login
     *
     * @param login a user login that should be checked
     * @return a {@code true} if the table has such row, {@code false} otherwise
     * @throws DAOException if a database access error occurs
     */
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

    /**
     * Updates a row in the table using user id
     * with new balance value
     *
     * @param userId  a user id
     * @param balance a new balance value
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Updates a row in the table using user id
     * with new password value
     *
     * @param userId   a user id
     * @param password a new password value
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Updates a row in the table using user id
     * with new firstName and lastName values
     *
     * @param userId    a user id
     * @param firstName a new first name value
     * @param lastName  a new last name value
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Updates a row in the table using user id
     * with new phone value
     *
     * @param userId a user id
     * @param phone  a new phone value
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Updates a row in the table using user id
     * with new active status value
     *
     * @param userId a user id
     * @param active a new active status value (true if user is not banned, false otherwise)
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Updates a row in the table using user id
     * with new values - loyalty points, active status and role id
     *
     * @param userId        a user id
     * @param loyaltyPoints a new loyalty points value
     * @param active        a new active value (true if user is not banned, false otherwise)
     * @param roleId        a new user role id value
     * @throws DAOException if {@code DaoException} occurs (database access error) or
     *                      if now rows where updated
     */
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

    /**
     * Creates a new {@code User} object and
     * sets its values using {@code ResultSet}
     *
     * @param rs a {@code ResultSet} to build an object
     * @return a {@code User}
     */
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
