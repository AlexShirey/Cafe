package com.shirey.cafe.logic;

import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

/**
 * The {@code GuestLogic} class
 * contains methods to provide guest logic
 *
 * @author Alex Shirey
 */

public class GuestLogic {

    private UserDAO userDAO = new UserDAO();

    /**
     * Gets (after finding and creating) a {@code User} object from a database using login and password.
     *
     * @param login    a user login
     * @param password a user password
     * @return a {@code User} object, or null if no row is presented in database with such parameters
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public User findUserByLoginAndPass(String login, String password) throws LogicException {

        try {
            return userDAO.findUserByLoginAndPass(login, password);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Checks, if this login is free (no rows in database with such login)
     *
     * @param login a login value to check
     * @return a {@code true} if this login is free, {@code false} otherwise
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public boolean isLoginFree(String login) throws LogicException {

        try {
            return !userDAO.isExist(login);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Creates and returns (after update a database) new {@code User} object
     * with this parameters.
     *
     * @param login     a login value
     * @param password  a password value
     * @param firstName a firstName value
     * @param lastName  a lastName value
     * @param phone     a phone value
     * @return a {@code User} object, not null
     * @throws LogicException if {@code DaoException} occurs (database access error)
     */
    public User registerUser(String login, String password, String firstName, String lastName, String phone) throws LogicException {

        User user = new User(login, password, firstName, lastName, phone);
        try {
            userDAO.create(user);
            user = userDAO.findEntityById(user.getUserId());
            if (user == null) {
                throw new LogicException("can't register user with this parameters");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return user;
    }

}





