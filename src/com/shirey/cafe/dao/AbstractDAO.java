package com.shirey.cafe.dao;

import com.shirey.cafe.db.ProxyConnection;
import com.shirey.cafe.entity.Entity;
import com.shirey.cafe.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<K, T extends Entity> {

    public abstract T findEntityById(K id) throws DAOException;

    public abstract List<T> findAll() throws DAOException;

    void returnConnection(ProxyConnection proxyConnection) throws DAOException {
        try {
            if (proxyConnection != null) {
                proxyConnection.setAutoCommit(true);
                proxyConnection.close();
            }
        } catch (SQLException e) {
            throw new DAOException("problems with returning proxy connection to connection pool, connection wasn't returned.", e);
        }
    }

}


