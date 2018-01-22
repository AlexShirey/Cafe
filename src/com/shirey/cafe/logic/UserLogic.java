package com.shirey.cafe.logic;

import com.shirey.cafe.dao.UserDAO;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.DAOException;
import com.shirey.cafe.exception.LogicException;

import java.util.List;

public class UserLogic {

    private static UserDAO userDAO = new UserDAO();

    //ok+
    public void changePassword(User user, String password) throws LogicException {

        try {
            userDAO.updatePassword(user.getUserId(), password);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setPassword(password);
    }

    //ok+
    public void changeNames(User user, String firstName, String lastName) throws LogicException {

        try {
            userDAO.updateNames(user.getUserId(), firstName, lastName);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }

    //ok+
    public void changePhone(User user, String phone) throws LogicException {

        try {
            userDAO.updatePhone(user.getUserId(), phone);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setPhone(phone);
    }

    //ok+
    public void banUser(User user) throws LogicException {

        try {
            userDAO.updateActiveStatus(user.getUserId(), false);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        user.setActive(false);

    }

    //ok+
    public User findUserById(int userId) throws LogicException {
        try {
            return userDAO.findEntityById(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


}
