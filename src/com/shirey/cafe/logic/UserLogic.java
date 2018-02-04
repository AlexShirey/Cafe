package com.shirey.cafe.logic;

import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.util.List;

/**
 * The {@code UserLogic} class
 * contains methods to provide user logic
 *
 * @author Alex Shirey
 */

public class UserLogic {

    private static UserDAO userDAO = new UserDAO();

    /**
     * Updates a database with new password value,
     * sets this value to the current {@code User} object after the update.
     *
     * @param user     a {@code User} object to change
     * @param password a new password value
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void changePassword(User user, String password) throws LogicException {

        try {
            userDAO.updatePassword(user.getUserId(), password);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setPassword(password);
    }

    /**
     * Updates a database with new firstName and lastName values,
     * sets this values to the current {@code User} object after the update.
     *
     * @param user      a {@code User} object to change
     * @param firstName a new firstName value
     * @param lastName  a new lastName value
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void changeNames(User user, String firstName, String lastName) throws LogicException {

        try {
            userDAO.updateNames(user.getUserId(), firstName, lastName);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }

    /**
     * Updates a database with new phone value,
     * sets this value to the current {@code User} object after the update.
     *
     * @param user  a {@code User} object to change
     * @param phone a new phone value
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void changePhone(User user, String phone) throws LogicException {

        try {
            userDAO.updatePhone(user.getUserId(), phone);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setPhone(phone);
    }

    /**
     * Updates a database with active status {@code false},
     * sets this status to the current {@code User} object after the update.
     *
     * @param user a {@code User} object to ban
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public void banUser(User user) throws LogicException {

        try {
            userDAO.updateActiveStatus(user.getUserId(), false);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setActive(false);

    }

    /**
     * Gets (after finding and creating) a {@code User} object from a database using userId.
     *
     * @param userId a user id to find and create the {@code User} object
     * @return a founded and created {@code User} object
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public User findUserById(int userId) throws LogicException {
        try {
            return userDAO.findEntityById(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets users with review from a database.
     *
     * @return a list contains {@code User}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<User> findUsersWithReview() throws LogicException {

        try {
            return userDAO.findUsersWithReview();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Gets users with at least one order from a database.
     *
     * @return a list contains {@code User}, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public List<User> findUsersWithOrders() throws LogicException {

        try {
            return userDAO.findUsersWithOrders();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


}
