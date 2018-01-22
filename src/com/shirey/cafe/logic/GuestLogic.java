package com.shirey.cafe.logic;

import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

public class GuestLogic {

    private UserDAO userDAO = new UserDAO();

    //ok+
    public User findUserByLoginAndPass(String login, String password) throws LogicException {

        try {
            return userDAO.findUserByLoginAndPass(login, password);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
    public boolean isLoginFree(String login) throws LogicException {

        try {
            return !userDAO.isExist(login);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    //ok+
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





